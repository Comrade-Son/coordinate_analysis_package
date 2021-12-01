package signal_analysis;

import java.util.ArrayList;

public class SignalAnalysis{
    public static class Complex{
        public double real;
        public double imag;

        public void setAttribute(double real, double imag){
            this.real = real;
            this.imag = imag;
        }
    }

    public static class Signal{
        final double SPEED_OF_SOUND = 344.92;
        public ArrayList<Complex> signal_array;
        public ArrayList<Complex> dft_array;
        public boolean is_steep;

        private void setAttribute(Complex complex){
            if (this.signal_array == null){
                this.signal_array = new ArrayList<>();
                this.signal_array.add(complex);
            }else{
                this.signal_array.add(complex);
            }
        }

        public void transformRealToComplex(double[] signal){
            this.signal_array = new ArrayList<>();
            for (int i = 0; i < signal.length; i++){
                // 信号を複素数型で受け取る
                Complex c = new Complex();
                c.setAttribute(signal[i], 0);
                this.setAttribute(c);
            }
        }

        private void dft(){
            int N = this.signal_array.size();
            double real = 0;
            double imag = 0;

            //実数部分と虚数部分に分けてフーリエ変換
            for (int n = 0; n < N; n++){
                real = 0;
                imag = 0;
                for (int k = 0; k < N; k++){
                    real += this.signal_array.get(k).real * Math.cos(Math.toRadians(2 * 180 * k * n / N));
                    imag -= this.signal_array.get(k).real * Math.sin(Math.toRadians(2 * 180 * k * n / N));
                }

                if (Math.abs(real) < Math.pow(10, -6)){
                    real = 0;
                }

                if (Math.abs(imag) < Math.pow(10, -6)){
                    imag = 0;
                }

                Complex dft_element = new Complex();
                dft_element.setAttribute(real, imag);

                if (this.dft_array == null){
                    this.dft_array = new ArrayList<>();
                    this.dft_array.add(dft_element);
                }else{
                    this.dft_array.add(dft_element);
                }
            }
        }

        public double getFrequencyAbs(int detect_index){
            double frequency_abs = 0;
            frequency_abs = Math.sqrt(Math.pow(this.dft_array.get(detect_index).real, 2) + Math.pow(this.dft_array.get(detect_index).imag, 2));
            return frequency_abs;
        }

        public void setSteepDetectArray(InputSignalInfo signal_info){
            this.dft();
            this.is_steep = signal_info.isDetectSteepIndex(this, signal_info.detect_freqency);
        }

        public double getDistance(InputSignalInfo signal_info, int order){
            double distance = 0;
            double sampling_timing = signal_info.sampling_timing;
            distance = (double)order * SPEED_OF_SOUND * sampling_timing;
            return distance;
        }
    }

    public static class DividedSignal{
        public ArrayList<Signal> divided_signal;

        public void setAttribute(double[] original_signal, InputSignalInfo signal_info){
            this.divided_signal = new ArrayList<Signal>();
            int fourier_batch_size = signal_info.fourier_batch_size;
            int fourier_iteration = signal_info.fourier_iteration;
            int n = signal_info.n;
            double[][] temp_signal_array = new double[fourier_iteration][fourier_batch_size];

            for (int i = 0; i < n; i++){  
                temp_signal_array[i / fourier_batch_size][i % fourier_batch_size] = original_signal[i];
            }

            for (int i = 0; i < fourier_iteration; i++){
                Signal signal = new Signal();
                signal.transformRealToComplex(temp_signal_array[i]);
                this.divided_signal.add(signal);
            }
        }
    }

    public static class InputSignalInfo{
        public int sampling_freqency;
        public double sampling_timing;
        public int detect_freqency;
        public int n;
        public double[] t;
        public double[] omega;
        public int fourier_batch_size;
        public int fourier_iteration;
        
        public void setAttribute(int sampling_freqency, int detect_freqency, int n, int fourier_batch_size){
            this.sampling_freqency = sampling_freqency;
            this.sampling_timing = 1 / (double)sampling_freqency;
            this.detect_freqency = detect_freqency;
            this.n = n;
            this.fourier_batch_size = fourier_batch_size;
            this.fourier_iteration = n / fourier_batch_size;
            this.t = new double[fourier_batch_size];
            this.omega = new double[fourier_batch_size];

            for (int i = 0; i < fourier_batch_size; i++){
                this.t[i] = this.sampling_timing * i;

                if (i <= (int)(fourier_batch_size / 2)){
                    this.omega[i] = ((double)sampling_freqency / (double)fourier_batch_size) * (double)i;
                }else{
                    this.omega[i] = ((double)sampling_freqency / (double)fourier_batch_size) * (double)(i - fourier_batch_size);
                }
            }
        }

        private int getApproximateFrequencyIndex(double detect_frequency){
            int detect_index = 0;
            double minimum_defference = 0;
            double comparison_frequency = 0;

            for (int i = 0; i < this.omega.length; i++){
                comparison_frequency = Math.abs(this.omega[i] - detect_frequency);
                if((i == 0) || (minimum_defference > comparison_frequency)){
                    detect_index = i;
                    minimum_defference = comparison_frequency;
                }else if(this.omega[i] < 0){
                    break;
                }
            }
            return detect_index;
        }

        private double takeAverageAround(Signal signal, int detect_index){
            int neighbor = 5;
            double average_frequency = 0;
            int searching_range = neighbor * 2 + 1;

            for (int i = 0; i < searching_range; i++){
                if (i == neighbor){
                    continue;
                }else{
                    average_frequency += signal.getFrequencyAbs(detect_index - neighbor + i);
                }
            }
            average_frequency = average_frequency / (double)(searching_range - 1);
            return average_frequency;
        }

        private boolean isSteep(Signal signal, int detect_index, double average_frequency){
            double steeping_threshold = 2;
            if (steeping_threshold * average_frequency < signal.getFrequencyAbs(detect_index)){
                return true;
            }else{
                return false;
            }
        }

        public boolean isDetectSteepIndex(Signal signal, double detect_frequency){
            int detect_index = getApproximateFrequencyIndex(detect_frequency);
            double average_frequency = takeAverageAround(signal, detect_index);
            boolean is_steep = isSteep(signal, detect_index, average_frequency);
            return is_steep;
        }
    }
}
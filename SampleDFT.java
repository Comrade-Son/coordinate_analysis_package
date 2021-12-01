import signal_analysis.SignalAnalysis.InputSignalInfo;
import signal_analysis.InterfaceDistance;

public class SampleDFT {
    public static void main(String[] args) {
        int sampling_frequency = 96000;
        double sampling_timing = 1 / (double)sampling_frequency;
        int detect_frequency = 15000;
        int n = 96000;
        int fourier_batch_size = 100;

        // 入力信号に関わる情報を決める
        InputSignalInfo signal_info = new InputSignalInfo();
        signal_info.setAttribute(sampling_frequency, detect_frequency, n, fourier_batch_size);

        // 信号を定義(これはなんでも良い)
        double[] signal = new double[n];
        double t = 0;
        double degree = 0;
        for (int i = 0; i < n; i++){
            t = sampling_timing * i;
            if (i <= 500){
                degree = Math.toRadians(2 * 180 * 1000 * t);
                signal[i] = Math.sin(degree);
            }else{
                degree = Math.toRadians(2 * 180 * detect_frequency * t);
                signal[i] = Math.sin(Math.toRadians(2 * 180 * detect_frequency * t));
            }
        }

        double distance = InterfaceDistance.getDistance(signal_info, signal);
        System.out.println(distance);
    }
}
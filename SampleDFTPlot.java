import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import signal_analysis.SignalAnalysis.DividedSignal;
import signal_analysis.SignalAnalysis.InputSignalInfo;

public class SampleDFTPlot {
    private static boolean checkBeforeWritefile(File file){
        if (file.exists()){
            if (file.isFile() && file.canWrite()){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int sampling_frequency = 96000;
        double sampling_timing = 1 / (double)sampling_frequency;
        int detect_frequency = 15000;
        int n = 96000;
        int fourier_batch_size = 1000;

        // 入力信号に関わる情報を決める
        InputSignalInfo signal_info = new InputSignalInfo();
        signal_info.setAttribute(sampling_frequency, detect_frequency, n, fourier_batch_size);

        // 信号を定義(これはなんでも良い)
        double[] signal = new double[n];
        double t = 0;
        double degree = 0;
        for (int i = 0; i < n; i++){
            t = sampling_timing * i;
            if (i < (96000 / 2)){
                degree = Math.toRadians(2 * 180 * 1000 * t);
                signal[i] = Math.sin(degree);
            }else{
                degree = Math.toRadians(2 * 180 * detect_frequency * t);
                signal[i] = Math.sin(Math.toRadians(2 * 180 * detect_frequency * t));
            }
        }

        // 信号を扱うクラス
        DividedSignal y_array = new DividedSignal();
        y_array.setAttribute(signal, signal_info);
        System.out.println(signal_info.fourier_iteration);
        for (int i = 0; i < signal_info.fourier_iteration; i++){
            // System.out.println(y_array.divided_signal.size());
            // System.exit(0);
            y_array.divided_signal.get(i).setSteepDetectArray(signal_info);
            System.out.println(y_array.divided_signal.get(i).is_steep);
            // if (y_array.divided_signal.get(i).is_steep){
            //     System.out.println(i);
            //     System.out.println(y_array.divided_signal.get(i).getDistance(signal_info, i * signal_info.fourier_batch_size));
            //     break;
            // }
        }

        try{
            File file = new File("/Users/matsuurahirokazu/works/comrade_son/coodinate_analysis_package/signal2.txt");
      
            if (checkBeforeWritefile(file)){
                FileWriter filewriter = new FileWriter(file);
                // y_array.divided_signal.get(0).signal_array.get(i);
                // y_array.divided_signal.get(0).dft_array.get(i);
                for (int i = 0; i < signal_info.fourier_batch_size; i++){
                    
                    filewriter.write(String.valueOf(signal_info.t[i]) + "\t" + String.valueOf(y_array.divided_signal.get(50).signal_array.get(i).real) + "\n");
                }

                filewriter.close();
            }else{
                System.out.println("ファイルに書き込めません");
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
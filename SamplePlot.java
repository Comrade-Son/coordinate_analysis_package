import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import signal_analysis.SignalAnalysis.Signal;
import signal_analysis.SignalAnalysis.InputSignalInfo;

public class SamplePlot {
    public static void main(String[] args) {
        int sampling_frequency = 94000;
        double sampling_timing = 1 / (double)sampling_frequency;
        int detect_frequency = 15000;
        int fourier_section = 50;
        int n = 94000;

        // 信号周辺情報を決める
        InputSignalInfo signal_info = new InputSignalInfo();
        signal_info.setAttribute(sampling_frequency, detect_frequency, n, fourier_section);

        // 信号を定義(これはなんでも良い)
        double[] signal = new double[n];
        double t = 0;
        double degree = 0;
        for (int i = 0; i < n; i++){
            t = sampling_timing * i;
            if (i <= 1000){
                degree = Math.toRadians(2 * 180 * 40 * t);
                signal[i] = Math.sin(degree);
            }else{
                degree = Math.toRadians(2 * 180 * detect_frequency * t);
                signal[i] = Math.sin(Math.toRadians(2 * 180 * detect_frequency * t));
            }
        }

        // 信号を扱うクラス
        Signal y = new Signal();
        y.transformRealToComplex(signal);

        try{
            File file = new File("/Users/matsuurahirokazu/works/comrade_son/coodinate_analysis_package/signal.txt");
      
            if (checkBeforeWritefile(file)){
                FileWriter filewriter = new FileWriter(file);

                for (int i = 0; i < n; i++){
                    // System.out.println("t: " + String.valueOf(i * (1 / (double)sampling_frequency)) + " y: " + String.valueOf(y.signal_array.get(i).real));
                    filewriter.write(String.valueOf(i * (1 / (double)sampling_frequency)) + "\t" + String.valueOf(y.signal_array.get(i).real) + "\n");
                }

                filewriter.close();
            }else{
                System.out.println("ファイルに書き込めません");
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private static boolean checkBeforeWritefile(File file){
        if (file.exists()){
            if (file.isFile() && file.canWrite()){
                return true;
            }
        }
        return false;
    }
}
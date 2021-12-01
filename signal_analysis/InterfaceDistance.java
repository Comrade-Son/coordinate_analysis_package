// You should compile this file at one directory abobe up not here
package signal_analysis;

import signal_analysis.SignalAnalysis.DividedSignal;
import signal_analysis.SignalAnalysis.InputSignalInfo;

public class InterfaceDistance {
    public static double getDistance(InputSignalInfo signal_info, double[] signal){
        double distance = 0;
        DividedSignal y_array = new DividedSignal();
        y_array.setAttribute(signal, signal_info);
        for (int i = 0; i < signal_info.fourier_iteration; i++){
            y_array.divided_signal.get(i).setSteepDetectArray(signal_info);
            if (y_array.divided_signal.get(i).is_steep){
                distance = y_array.divided_signal.get(i).getDistance(signal_info, i * signal_info.fourier_batch_size);
                return distance;
            }
        }
        System.out.println("Speaker client does not exist");
        return distance;
    }
}
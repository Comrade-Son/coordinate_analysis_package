import node_infomation.NodeInfomation.Node;
import node_infomation.NodeInfomation.NodeSets;
import node_infomation.NodeInfomation.NodeToNode;
import node_infomation.NodeInfomation.NodeToNodeSets;
import node_infomation.NodeInfomation.CalculateCoordinate;
import signal_analysis.SignalAnalysis.InputSignalInfo;
import signal_analysis.InterfaceDistance;

public class SampleCoordinate {
    public static void main(String[] args) {

        // infomation: client to client
        NodeSets node_sets = new NodeSets();
        NodeToNodeSets n2n_sets = new NodeToNodeSets();
        InputSignalInfo signal_info = new InputSignalInfo();
        int sampling_freqency = 48000;
        int detect_freqency = 15000;
        int n = 3000;
        int fourier_section = 30;

        String[] device_id = {"0", "1", "2", "3"};
        int[] device_index = {0, 1, 2, 3};
        double[][] coordinate = {{0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}};

        // host -> client は行わない
        int[] from = {1, 1, 1, 2, 2, 2, 3, 3, 3};
        int[] to = {0, 2, 3, 0, 1, 3, 0, 1, 2};
        double[] distance = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        // signal_info
        signal_info.setAttribute(sampling_freqency, detect_freqency, n, fourier_section);

        // 信号と送受信方向の対応表
        int[][] from_to_array = {
            {1, 2},
            {1, 3},
            {2, 1},
            {2, 3},
            {3, 1},
            {3, 2},
            {1, 0},
            {2, 0},
            {3, 0}
        };

        // input signal
        double[] t = new double[signal_info.n];
        double[][] signal = new double[3 * 4][n];
        double degree = 0;
        int noise_frequency = 1000;
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < n; j++){
                t[j] = (double)j * signal_info.sampling_timing;
                if ((i < 6) && (j <= 278)){
                    degree = 2 * 180 * noise_frequency * t[j];
                    signal[i][j] = Math.sin(Math.toRadians(degree));
                }else if ((i >= 6) && (j <= 161)){
                    degree = 2 * 180 * noise_frequency * t[j];
                    signal[i][j] = Math.sin(Math.toRadians(degree));
                }else{
                    degree = 2 * 180 * detect_freqency * t[j];
                    signal[i][j] = Math.sin(Math.toRadians(degree)); 
                }
            } 
        }

        // NodeSets initializes
        for (int i = 0; i < coordinate.length; i++){
            Node node = new Node();
            node.setValue(device_id[i], device_index[i]);
            node_sets.setValue(node);
        }

        // NodeToNodeSets which does not has information of distance initializes
        for (int i = 0; i < distance.length; i++){
            NodeToNode n2n = new NodeToNode();
            n2n.setValue(from[i], to[i], distance[i]);
            n2n_sets.setValue(n2n);
        }


        for (int i = 0; i < distance.length; i++){
            distance[i] = InterfaceDistance.getDistance(signal_info, signal[i]);
        }

        for (int i = 0; i < distance.length; i++){
            n2n_sets.updateDistance(from_to_array[i][0], from_to_array[i][1], distance[i]);
        }

        CalculateCoordinate calculate_coordinate = new CalculateCoordinate();
         
        calculate_coordinate.setNode(node_sets, n2n_sets);

        for (int i = 0; i < node_sets.node_sets.size(); i++){
            if (i == 0){
                System.out.println("host(phone): {" + String.valueOf(node_sets.node_sets.get(i).coordinate.x) + ", " + String.valueOf(node_sets.node_sets.get(i).coordinate.y) + "}");
            }else{
                System.out.println("client(spresense)" + node_sets.node_sets.get(i).device_id + ": {" + String.valueOf(node_sets.node_sets.get(i).coordinate.x) + ", " + String.valueOf(node_sets.node_sets.get(i).coordinate.y) + "}");
            }
        }
    }
}

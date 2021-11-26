import node_infomation.NodeInfomation.Coodinate;
import node_infomation.NodeInfomation.Node;
import node_infomation.NodeInfomation.NodeSets;
import node_infomation.NodeInfomation.NodeToNode;
import node_infomation.NodeInfomation.NodeToNodeSets;
import node_infomation.NodeInfomation.CalcCoodinate;

public class Main {
    public static void main(String[] args) {
        NodeSets node_sets = new NodeSets();

        String[] device_id = {"10", "11", "12", "13"};
        int[] device_index = {0, 1, 2, 3};
        double[][] coodinate = {{0.0, 0.0}, {0.5, 0.0}, {0.5, 0.5}, {0.0, 0.5}};

        for (int i = 0; i < coodinate.length; i++){
            Node node = new Node();
            node.setValue(device_id[i], device_index[i]);
            node_sets.setValue(node);
        }
        
        int node_index = node_sets.getIndex(1);

        System.out.println(node_sets.node_sets.get(node_index).device_id);

        NodeToNodeSets n2n_sets = new NodeToNodeSets();
        int[] from = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3};
        int[] to = {1, 2, 3, 0, 2, 3, 0, 1, 3, 0, 1, 2};
        double[] distance = {0.4, 0.5, 0.3, 0.4, 0.3, 0.5, 0.5, 0.3, 0.4, 0.3, 0.5, 0.4};

        for (int i = 0; i < distance.length; i++){
            NodeToNode n2n = new NodeToNode();
            n2n.setValue(from[i], to[i], distance[i]);
            n2n_sets.setValue(n2n);
        }

        System.out.println(n2n_sets.getDistance(1, 3));

        CalcCoodinate calc_coodinate = new CalcCoodinate();
        calc_coodinate.setNode(node_sets, n2n_sets);
        int idx = node_sets.getIndex(3);
        System.out.println(node_sets.node_sets.get(idx).coodinate.x);
        System.out.println(node_sets.node_sets.get(idx).coodinate.y);
    }
}
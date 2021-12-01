package node_infomation;

import java.util.ArrayList;
import java.lang.Math;

public class NodeInfomation {
    public static class TrigonometricRatio{
        public double cos;
        public double sin;

        public void setValue(double cos, double sin){
            this.cos = cos;
            this.sin = sin;
        }
    }

    public static class TrigonometricFunction{

        public static double convertSideToCos(double left_side, double right_side, double opposit_side){
            double cos = (Math.pow(left_side, 2) + Math.pow(right_side, 2) - Math.pow(opposit_side, 2)) / (2 * left_side * right_side);
            return cos;
        }

        public static double convertCosToSin(double cos){
            double sin = Math.sqrt(1 - Math.pow(cos, 2));
            return sin;
        }

        public static TrigonometricRatio sideToTriRatio(double left_side, double right_side, double opposit_side){
            double cos = TrigonometricFunction.convertSideToCos(left_side, right_side, opposit_side);
            double sin = TrigonometricFunction.convertCosToSin(cos);
            TrigonometricRatio tri_ratio = new TrigonometricRatio();
            tri_ratio.setValue(cos, sin);
            return tri_ratio;
        }

        public static double sumVecotorAngle(Coodinate host, Coodinate client1, Coodinate client2, Coodinate client3){
            double theta = 0;
            double left_side = 0;
            double right_side = 0;
            double opposit_side = 0;

            left_side = LinePointFunction.getDistance(host, client1);
            right_side = LinePointFunction.getDistance(host, client2);
            opposit_side = LinePointFunction.getDistance(client1, client2);
            TrigonometricRatio tri_rat1 = new TrigonometricRatio();
            tri_rat1 = sideToTriRatio(left_side, right_side, opposit_side);

            left_side = LinePointFunction.getDistance(host, client2);
            right_side = LinePointFunction.getDistance(host, client3);
            opposit_side = LinePointFunction.getDistance(client2, client3);
            TrigonometricRatio tri_rat2 = new TrigonometricRatio();
            tri_rat2 = sideToTriRatio(left_side, right_side, opposit_side);

            left_side = LinePointFunction.getDistance(host, client3);
            right_side = LinePointFunction.getDistance(host, client1);
            opposit_side = LinePointFunction.getDistance(client3, client1);
            TrigonometricRatio tri_rat3 = new TrigonometricRatio();
            tri_rat3 = sideToTriRatio(left_side, right_side, opposit_side);
            
            theta = Math.acos(tri_rat1.cos) + Math.acos(tri_rat2.cos) + Math.acos(tri_rat3.cos);
            return theta;
        }
    }

    public static class LinePointFunction{
        private static double getDistance(Coodinate a, Coodinate b) {
            double distance = Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
            return distance;
        }

        public static Coodinate getNeighborhood(Coodinate basis, Coodinate comparison1, Coodinate comparison2, double basis_distance){
            double distance1 = LinePointFunction.getDistance(basis, comparison1);
            double distance2 = LinePointFunction.getDistance(basis, comparison2);
            double difference = Math.abs(basis_distance - distance1) - Math.abs(basis_distance - distance2);
            if (difference < 0){
                return comparison1;
            }else{
                return comparison2;
            } 
        }

        // public static Coodinate CenterOfGravity(Coodinate p, Coodinate q){
        //     Coodinate g = new Coodinate();
        //     double[] g_array = {(p.x + q.x) / 2.0, (p.y + q.y) / 2.0};
        //     g.setValue(g_array);
        //     return g;
        // }
    }

    public static class Coodinate{
        public double x;
        public double y;

        public void setValue(double[] coodinate_array){
            this.x = coodinate_array[0];
            this.y = coodinate_array[1];
        }
    }

    public static class Node{
        public String device_id;
        public int device_index;
        public Coodinate coodinate;

        public void setValue(String device_id, int device_index){
            Coodinate coodinate =  new Coodinate();
            double[] coodinate_array = new double[2];
            coodinate.setValue(coodinate_array);

            this.device_id = device_id;
            this.device_index = device_index;
            this.coodinate = coodinate;
        }

        public void updateCoodinate(Coodinate coodinate){
            this.coodinate = coodinate;
        }

        public void updateDoubleArrayToCoodinate(double[] coodinate_array){
            Coodinate coodinate =  new Coodinate();
            coodinate.setValue(coodinate_array);
            this.coodinate = coodinate;
        }
    }

    public static class NodeSets{
        public ArrayList<Node> node_sets;

        public void setValue(Node new_node){
            if (this.node_sets == null){
                this.node_sets = new ArrayList<>();
                this.node_sets.add(new_node);
            }else{
                this.node_sets.add(new_node);
            }
        }

        public int getIndex(int index){
            if (this.node_sets.size() <= 0){
                System.out.println("the array is empty so set the value");
                return -1;
            }else{
                for (int i = 0; i < this.node_sets.size(); i++){
                    if (this.node_sets.get(i).device_index == index){
                        return i;
                    }
                }
            }
            System.out.println("choosing index is not found");
            return -1;
        }

        public int getId(String id){
            if (this.node_sets.size() <= 0){
                System.out.println("the array is empty so set the value");
                return -1;
            }else{
                for (int i = 0; i < this.node_sets.size(); i++){
                    if (this.node_sets.get(i).device_id.equals(id)){
                        return i;
                    }
                }
            }
            System.out.println("choosing index is not found");
            return -1;
        }

        public Coodinate deffernceCoodinate(int origin_index, int moved_index){
            int origin_in_array_index = this.getIndex(origin_index);
            int moved_in_array_index = this.getIndex(moved_index);

            Coodinate origin_coodinate = this.node_sets.get(origin_in_array_index).coodinate;
            Coodinate moved_coodinate = this.node_sets.get(moved_in_array_index).coodinate;

            moved_coodinate.x -= origin_coodinate.x;
            moved_coodinate.y -= origin_coodinate.y;

            return moved_coodinate;
        }
    }

    public static class NodeToNode{
        public int to;
        public int from;
        public double distance;

        public void setValue(int from, int to, double distance){
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }

    public static class NodeToNodeSets{
        public ArrayList<NodeToNode> n2n_sets;

        public void setValue(NodeToNode new_n2n){
            if (this.n2n_sets == null){
                this.n2n_sets = new ArrayList<>();
                this.n2n_sets.add(new_n2n);
            }else{
                this.n2n_sets.add(new_n2n);
            }
        }

        public void updateDistance(int reference_node_from, int reference_node_to, double distance){
            for (int i = 0; i < this.n2n_sets.size(); i++){
                if ((this.n2n_sets.get(i).from == reference_node_from) && (this.n2n_sets.get(i).to == reference_node_to)){
                    this.n2n_sets.get(i).distance = distance;
                }
            }
        }

        public double getDistance(int reference_node_from, int reference_node_to){
            if (this.n2n_sets.size() <= 0){
                System.out.println("the array is empty so set the value");
                return 0;
            }else{
                for (int i = 0; i < this.n2n_sets.size(); i++){
                    if (this.n2n_sets.get(i).from == reference_node_from && this.n2n_sets.get(i).to == reference_node_to){
                        return this.n2n_sets.get(i).distance;
                    }
                }
                System.out.println("choosing index is not found");
                return 0;
            }
        }
    }

    public static class CalculateCoodinate{

        private void setFirstNode(NodeSets node_sets){
            int node_index = node_sets.getIndex(0);
            double[] coodinate = {0.0, 0.0};
            node_sets.node_sets.get(node_index).updateDoubleArrayToCoodinate(coodinate);
        }

        private void setSecondNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            int node_index = node_sets.getIndex(1);
            double distance = n2n_sets.getDistance(1, 0);
            double[] coodinate = {distance, 0.0};
            node_sets.node_sets.get(node_index).updateDoubleArrayToCoodinate(coodinate);
        }

        private void setThirdNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            int node_index = node_sets.getIndex(2);
            double distance_1_0 = n2n_sets.getDistance(1, 0);
            double distance_2_0 = n2n_sets.getDistance(2, 0);
            double distance_1_2 = n2n_sets.getDistance(1, 2);
            TrigonometricRatio tri_ratio = TrigonometricFunction.sideToTriRatio(distance_1_0, distance_2_0, distance_1_2);
            double[] coodinate_array = {distance_2_0 * tri_ratio.cos, distance_2_0 * tri_ratio.sin};
            node_sets.node_sets.get(node_index).updateDoubleArrayToCoodinate(coodinate_array);
        }

        private void setHostNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            int node_index = node_sets.getIndex(3);
            double distance_1_0 = n2n_sets.getDistance(1, 0);
            double distance_3_0 = n2n_sets.getDistance(3, 0);
            double distance_1_3 = n2n_sets.getDistance(1, 3);
            double distance_2_3 = n2n_sets.getDistance(2, 3);
            TrigonometricRatio tri_ratio = TrigonometricFunction.sideToTriRatio(distance_1_0, distance_3_0, distance_1_3);

            double[][] coodinate_array = {
                {distance_3_0 * tri_ratio.cos, distance_3_0 * tri_ratio.sin},
                {distance_3_0 * tri_ratio.cos, (-1) * distance_3_0 * tri_ratio.sin},
            };

            Coodinate coodinate3_up = new Coodinate();
            Coodinate coodinate3_down = new Coodinate();

            coodinate3_up.setValue(coodinate_array[0]);
            coodinate3_down.setValue(coodinate_array[1]);

            double comparison_up = LinePointFunction.getDistance(node_sets.node_sets.get(node_sets.getIndex(2)).coodinate, coodinate3_up);
            double comparison_down = LinePointFunction.getDistance(node_sets.node_sets.get(node_sets.getIndex(2)).coodinate, coodinate3_down);

            if (Math.abs(distance_2_3 - comparison_up) <= Math.abs(distance_2_3 - comparison_down)){
                node_sets.node_sets.get(node_index).updateDoubleArrayToCoodinate(coodinate_array[0]);
            }else{
                node_sets.node_sets.get(node_index).updateDoubleArrayToCoodinate(coodinate_array[1]);
            }   
        }

        public void setNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            this.setFirstNode(node_sets);
            this.setSecondNode(node_sets, n2n_sets);
            this.setThirdNode(node_sets, n2n_sets);
            this.setHostNode(node_sets, n2n_sets);
        }
    }

    // public static class CalculateHostCoodinate{

    //     private Coodinate estimateHostCoodinate(NodeToNodeSets n2n_sets, int host_index, int client1_index, int client2_index, Boolean is_abobe){
    //         double distance_left = n2n_sets.getDistance(host_index, client1_index);
    //         double distance_right = n2n_sets.getDistance(host_index, client2_index);
    //         double distance_opposit = n2n_sets.getDistance(client1_index, client2_index);
    //         TrigonometricRatio tri_ratio = TrigonometricFunction.sideToTriRatio(distance_left, distance_right, distance_opposit);

    //         Coodinate coodinate = new Coodinate();
    //         if (is_abobe){
    //             double[] coodinate_array = {distance_left * tri_ratio.cos, distance_left * tri_ratio.sin};
    //             coodinate.setValue(coodinate_array);
    //             return coodinate;
    //         }else{
    //             double[] coodinate_array = {distance_left * tri_ratio.cos, (-1) * distance_left * tri_ratio.sin};
    //             coodinate.setValue(coodinate_array);
    //             return coodinate;
    //         }
    //     }

    //     private Coodinate getHostCoodinate(NodeSets node_sets, NodeToNodeSets n2n_sets){
    //         // 座標をリターンする
    //         double[] sum_thata = new double[2];
    //         ArrayList<Coodinate> coodinate = new ArrayList<Coodinate>();

    //         for (int i = 0; i < sum_thata.length; i++){
    //             Boolean is_abobe = (1 == i);
    //             Coodinate host_coodinate = estimateHostCoodinate(n2n_sets, 0, i + 1, i + 2, is_abobe);
    //             sum_thata[i] = TrigonometricFunction.sumVecotorAngle(host_coodinate, node_sets.node_sets.get(node_sets.getIndex(1)).coodinate, node_sets.node_sets.get(node_sets.getIndex(2)).coodinate, node_sets.node_sets.get(node_sets.getIndex(3)).coodinate);
    //             coodinate.add(host_coodinate);
    //         }

    //         if (sum_thata[0] >= sum_thata[1]){
    //             return coodinate.get(0);
    //         }else{
    //             return coodinate.get(1);
    //         }
    //     }

    //     private void setHostCoodinate(NodeSets node_sets, NodeToNodeSets n2n_sets){
    //         int host_index = node_sets.getIndex(0);
    //         Coodinate host_coodinate = new Coodinate();
    //         host_coodinate = getHostCoodinate(node_sets, n2n_sets);
    //         node_sets.node_sets.get(host_index).updateCoodinate(host_coodinate);
    //     }

    //     private void alignHostToOrigin(NodeSets node_sets){
    //         int host_index = node_sets.getIndex(0);
    //         Coodinate moved_coodinate = new Coodinate();
    //         for (int i = 0; i < node_sets.node_sets.size(); i++){
    //             moved_coodinate = node_sets.deffernceCoodinate(host_index, i);
    //             node_sets.node_sets.get(i).updateCoodinate(moved_coodinate);
    //         }
    //     }

    //     public void determineAllNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
    //         setHostCoodinate(node_sets, n2n_sets);
    //         alignHostToOrigin(node_sets);
    //     }
    // }
}
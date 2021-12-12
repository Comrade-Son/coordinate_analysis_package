package node_infomation;

import java.util.ArrayList;
import java.lang.Math;

public class NodeInfomation{
    public static class TrigonometricRatio{
        public double cos;
        public double sin;

        public void setValue(double cos, double sin){
            this.cos = cos;
            this.sin = sin;
        }
    }

    public static class TrigonometricFunction{

        protected static double convertSideToCos(double left_side, double right_side, double opposit_side){
            double cos = (Math.pow(left_side, 2) + Math.pow(right_side, 2) - Math.pow(opposit_side, 2)) / (2 * left_side * right_side);
            return cos;
        }

        protected static double convertCosToSin(double cos){
            double sin = Math.sqrt(1 - Math.pow(cos, 2));
            return sin;
        }

        protected static TrigonometricRatio sideToTriRatio(double left_side, double right_side, double opposit_side){
            double cos = TrigonometricFunction.convertSideToCos(left_side, right_side, opposit_side);
            double sin = TrigonometricFunction.convertCosToSin(cos);
            TrigonometricRatio tri_ratio = new TrigonometricRatio();
            tri_ratio.setValue(cos, sin);
            return tri_ratio;
        }
    }

    public static class LinePointFunction{
        protected static double getDistance(Coordinate a, Coordinate b) {
            double distance = Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
            return distance;
        }
    }

    public static class Coordinate{
        public double x;
        public double y;

        public void setValue(double[] coordinate_array){
            this.x = coordinate_array[0];
            this.y = coordinate_array[1];
        }
    }

    public static class Node{
        public String device_id;
        public int device_index;
        public Coordinate coordinate;

        public void setValue(String device_id, int device_index){
            Coordinate coordinate =  new Coordinate();
            double[] coordinate_array = new double[2];
            coordinate.setValue(coordinate_array);

            this.device_id = device_id;
            this.device_index = device_index;
            this.coordinate = coordinate;
        }

        public void updateCoordinate(Coordinate coordinate){
            this.coordinate = coordinate;
        }

        public void updateDoubleArrayToCoordinate(double[] coordinate_array){
            Coordinate coordinate =  new Coordinate();
            coordinate.setValue(coordinate_array);
            this.coordinate = coordinate;
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

        public Coordinate deffernceCoordinate(int origin_index, int moved_index){
            int origin_in_array_index = this.getIndex(origin_index);
            int moved_in_array_index = this.getIndex(moved_index);

            Coordinate origin_coordinate = this.node_sets.get(origin_in_array_index).coordinate;
            Coordinate moved_coordinate = this.node_sets.get(moved_in_array_index).coordinate;

            moved_coordinate.x -= origin_coordinate.x;
            moved_coordinate.y -= origin_coordinate.y;

            return moved_coordinate;
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

    public static class CalculateCoordinate{

        private void setHostNode(NodeSets node_sets){
            int node_index = node_sets.getIndex(0);
            double[] coordinate = {0.0, 0.0};
            node_sets.node_sets.get(node_index).updateDoubleArrayToCoordinate(coordinate);
        }

        private void setFirstNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            int node_index = node_sets.getIndex(1);
            double distance = n2n_sets.getDistance(1, 0);
            double[] coordinate = {distance, 0.0};
            node_sets.node_sets.get(node_index).updateDoubleArrayToCoordinate(coordinate);
        }

        private void setSecondNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            int node_index = node_sets.getIndex(2);
            double distance_1_0 = n2n_sets.getDistance(1, 0);
            double distance_2_0 = n2n_sets.getDistance(2, 0);
            double distance_1_2 = n2n_sets.getDistance(1, 2);
            TrigonometricRatio tri_ratio = TrigonometricFunction.sideToTriRatio(distance_1_0, distance_2_0, distance_1_2);
            double[] coordinate_array = {distance_2_0 * tri_ratio.cos, distance_2_0 * tri_ratio.sin};
            node_sets.node_sets.get(node_index).updateDoubleArrayToCoordinate(coordinate_array);
        }

        private void setThirdNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            int node_index = node_sets.getIndex(3);
            double distance_1_0 = n2n_sets.getDistance(1, 0);
            double distance_3_0 = n2n_sets.getDistance(3, 0);
            double distance_1_3 = n2n_sets.getDistance(1, 3);
            double distance_2_3 = n2n_sets.getDistance(2, 3);
            TrigonometricRatio tri_ratio = TrigonometricFunction.sideToTriRatio(distance_1_0, distance_3_0, distance_1_3);

            double[][] coordinate_array = {
                {distance_3_0 * tri_ratio.cos, distance_3_0 * tri_ratio.sin},
                {distance_3_0 * tri_ratio.cos, (-1) * distance_3_0 * tri_ratio.sin},
            };

            Coordinate coordinate3_up = new Coordinate();
            Coordinate coordinate3_down = new Coordinate();

            coordinate3_up.setValue(coordinate_array[0]);
            coordinate3_down.setValue(coordinate_array[1]);

            double comparison_up = LinePointFunction.getDistance(node_sets.node_sets.get(node_sets.getIndex(2)).coordinate, coordinate3_up);
            double comparison_down = LinePointFunction.getDistance(node_sets.node_sets.get(node_sets.getIndex(2)).coordinate, coordinate3_down);

            if (Math.abs(distance_2_3 - comparison_up) <= Math.abs(distance_2_3 - comparison_down)){
                node_sets.node_sets.get(node_index).updateDoubleArrayToCoordinate(coordinate_array[0]);
            }else{
                node_sets.node_sets.get(node_index).updateDoubleArrayToCoordinate(coordinate_array[1]);
            }   
        }

        public void setNode(NodeSets node_sets, NodeToNodeSets n2n_sets){
            this.setHostNode(node_sets);
            this.setSecondNode(node_sets, n2n_sets);
            this.setThirdNode(node_sets, n2n_sets);
            this.setFirstNode(node_sets, n2n_sets);
        }
    }
}
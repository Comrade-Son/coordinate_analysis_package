# node_information

`Abstruct`

* nodeの位置座標、node間の距離情報、node idなどの情報を扱うためのパッケージ

## NodeInformation

### structure

|class name|description|
|--|--|
|Coodinate|直交座標型の定義用クラス|
|Node|nodeの座標情報などを保存しておくためのクラス|
|NodeSets|Nodeを配列的に保存しておくためのクラス|
|NodeToNode|node同士の距離情報などを保存しておくためのクラス|
|NodeToNodeSets|NodeToNodeを配列的に保存しておくためのクラス|
|CalculateCoodinate|座標を計算し更新するためのクラス|

### discription

`TrigonometricRatio`

```java
public static class TrigonometricRatio{
    // attribute
    public double cos;
    public double sin;

    // method
    public void setValue(double cos, double sin){
        this.cos = cos;
        this.sin = sin;
    }
}
```

* あるthetaに対しての三角比を定義しておくためのクラス

`TrigonometricFunction`

```java
public static class TrigonometricFunction{
    // method
    protected static double convertSideToCos(double left_side, double right_side, double opposit_side);
    protected static double convertCosToSin(double cos);
    protected static TrigonometricRatio sideToTriRatio(double left_side, double right_side, double opposit_side);
    protected static double sumVecotorAngle(Coodinate host, Coodinate client1, Coodinate client2, Coodinate client3)
}
```

* 座標計算時の三角比に関わる計算をするときに利用するクラス

`LinePointFunction`

```java
public static class LinePointFunction{
    // method
    protected static double getDistance(Coodinate a, Coodinate b);
}
```

* 点同士の距離を計算するときに使用するクラス

`Coodinate`

```java
public static class Coodinate{
    // attribute
    public double x;
    public double y;

    // method
    public void setValue(double[] coodinate_array);
}
```

* 各nodeの座標情報を直交座標で表すためのクラス

`Node`

```java
public static class Node{
    // attribute
    public String device_id;
    public int device_index;
    public Coodinate coodinate;

    // method
    public void setValue(String device_id, int device_index);
    public void updateCoodinate(Coodinate coodinate);
    public void updateDoubleArrayToCoodinate(double[] coodinate_array);
}
```

* 各nodeの情報を記録しておくクラス
* host: device_id = 0, client: device_id >= 1
* 属性の代入は `setValue` を用いる
* 座標の上書きには `updateCoodinate` を用いる(`Coodinate`型を引数利用時)
* 座標の上書きには `updateDoubleArrayToCoodinate` を用いる(`double`型配列を引数利用時)

`NodeSets`

```java
public static class NodeSets{
    // attribute
    public ArrayList<Node> node_sets;

    // method
    public void setValue(Node new_node);
    public int getIndex(int index);
    public int getId(int index);
}
```

* 各Spresense/Clientの情報を配列的に記録しておくクラス
* 特定のインデックスを持った `Node` クラスにアクセスしたい時は `getIndex` で配列中のインデックスを取得できる
* 特定のIDを持った `Node` クラスにアクセスしたい時は `getId` で配列中のインデックスを取得できる

`NodeToNode`

```java
public static class NodeToNode{
    // attribute
    public int to;
    public int from;
    public double distance;

    // method
    public void setValue(int from, int to, double distance);
}
```

* 各Spresense/Client同士の距離の情報を記録しておくクラス
* 属性の代入は `setValue` を用いる

`NodeToNodeSets`

```java
public static class NodeToNodeSets{
    // attribute
    public ArrayList<NodeToNode> n2n_sets;

    // method
    public void setValue(NodeToNode new_n2n);
    public void updateDistance(int reference_node_from, int reference_node_to, double distance);
    public double getDistance(int reference_node_from, int reference_node_to);
}
```

* `NodeToNode` クラスを配列的に記録しておくクラス
* node同士 の距離を取得したいときは `getDistance` でその距離を取得できる

`CalculateCoodinate`

```java
public static class CalculateCoodinate{
    // method
    private void setHosttNode(NodeSets node_sets);
    private void setFirstNode(NodeSets node_sets, NodeToNodeSets n2n_sets);
    private void setSecondNode(NodeSets node_sets, NodeToNodeSets n2n_sets);
    private void setThirdtNode(NodeSets node_sets, NodeToNodeSets n2n_sets);
    public void setNode(NodeSets node_sets, NodeToNodeSets n2n_sets);
}
```

* `setNode`でhostを中心、client1をその右側として座標を推定しリターンする

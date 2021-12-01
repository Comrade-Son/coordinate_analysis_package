# spresense_coodinate

## `wave_information`

coming soon ...

## `node_information.NodeInfomation`

Spresense 同士の絶対距離を計測するのに用いる。

```java
public static class Node{
    // attribute
    public String device_id;
    public int device_index;
    public Coodinate coodinate;

    // method
    public void setValue(String device_id, int device_index);
    public void updateCoodinate(double[] coodinate_array);
}

public static class NodeSets{
    // attribute
    public ArrayList<Node> node_sets;

    // method
    public void setValue(Node new_node);
    public int getIndex(int index);
    public int getId(int index);
}
```

`Node` クラス

* 各Spresense/Clientの情報を記録しておくクラス
* device_id >= 0 でSpresense, device_id = -1 でClient
* 属性の代入は `setValue` を用いる
* 座標の上書きには `updateCoodinate` を用いる

`NodeSets` クラス

* 各Spresense/Clientの情報を配列的に記録しておくクラス
* 特定のインデックスを持った `Node` クラスにアクセスしたい時は `getIndex` で配列中のインデックスを取得できる
* 特定のIDを持った `Node` クラスにアクセスしたい時は `getId` で配列中のインデックスを取得できる

```java
public static class NodeToNode{
    // attribute
    public int to;
    public int from;
    public double distance;

    // method
    public void setValue(int from, int to, double distance);
}

public static class NodeToNodeSets{
    // attribute
    public ArrayList<NodeToNode> n2n_sets;

    // method
    public void setValue(NodeToNode new_n2n);
    public double getDistance(int reference_node_from, int reference_node_to);
}
```

`NodeToNode` クラス

* 各Spresense/Client同士の距離の情報を記録しておくクラス
* 属性の代入は `setValue` を用いる

`NodeToNodeSets` クラス

* `NodeToNode` クラスを配列的に記録しておくクラス
* Spresense A から Spresense B の距離を取得したいときは `getDistance` でその距離を取得できる

```java
public static class CalcCoodinate{
    // method
    public void setNode(NodeSets node_sets, NodeToNodeSets n2n_sets)
}
```

`CalcCoodinate` クラス

* `NodeSets` クラスと `NodeToNodeSets` クラスをもとに、各Spresenseの絶対位置をupdateするためのクラス

### スプレセンス - スプレセンス => スマホ

* 3つの波形情報 × 2種類　=> 距離に変換

### スプレセンス - スマホ => スマホ

* 4種類の波形情報 => 距離に変換

* 距離情報 => 4種類の誤差入り情報を入手

* 4種類の誤差入り情報 => 重心を求める

* 重心を返す(これが)

(スプレセンスとスマホ)

※ s0 ~ s3 で囲まれていると想定
s0 s1 p0 上側
s0 s2 p0 下側
※ この辺で行列回転の式必要そう
s0 s3 p0 s3のyが負-> 上側, s3のyが正-> 下側
=> 重心を判定

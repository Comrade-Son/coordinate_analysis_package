# signal_analysis

`Abstruct`

* `detected_signal`を発信した node からサンプリング周波数`sampling_frequency`の信号を受信
* 受信した信号を`iteration`だけ分割 (1区間のデータ数: `batch_size`)
* それぞれの区間について**離散フーリエ変換**を行い、周波数領域に変換
* `detected_signal`を分割した i 番目の区間に対して検出
* node間の距離 `distance` =  **i * SPEED_OF_SOUND * sampling_timing** と推定 (`sampling_timing =  1 / sampling_frequency`)

## SignalAnalysis

### structure

|class name|description|
|--|--|
|Complex|複素数型の数値を扱う|
|Signal|分割された信号の区間1つあたりの情報を扱う|
|DividedSignal|分割された信号情報を配列的に扱う|
|InputSignalInfo|入力された信号に関する周辺情報を扱う|

### concept

|**data content**|input all nodes' signal|one nodes' signal|divided chunk|intensity of one data|
|--|--|--|--|--|
|**management class name**|none|DevidedSignal|Signal|Complex|
|**length of component**|n|fourier_iteration|fourier_batch_size|1|

### discription

`Complex`

```java
private static class Complex{
    // atribute
    public double real;
    public double imag;

    // method
    public void setAttribute(double real, double imag);
}
```

`Signal`

```java
public static class Signal{
    // atribute
    final double SPEED_OF_SOUND = 344.92;
    public ArrayList<Complex> signal_array;
    public ArrayList<Complex> dft_array;
    public boolean is_steep;

    // method
    private void setAttribute(Complex complex);
    public void transformRealToComplex(double[] signal);
    private void dft();
    protected double getFrequencyAbs(int detect_index);
    public void setSteepDetectArray(InputSignalInfo signal_info);
    public double getDistance(InputSignalInfo signal_info, int order);
}
```

* nodeから入力信号を`Complex`型の値で保存する
* attributeのsetは`transformRealToComplex`(double型配列)で行う
* `this.signal_array`を **DFT(離散フーリエ変換)** して`this.dft_array`に保存
* 分割した信号のindexと信号に関する周辺情報を引数にとって`getDistance`によって距離を算出する

`DividedSignal`

```java
public static class DividedSignal{
    // attribute
    public ArrayList<Signal> divided_signal;

    // method
    public void setAttribute(double[] original_signal, InputSignalInfo signal_info)
}
```

`InputSignalInfo`

```java
public static class InputSignalInfo{
    // attribute
    public int sampling_freqency;
    public double sampling_timing;
    public int detect_freqency;
    public int n;
    public double[] t;
    public double[] omega;
    public int fourier_batch_size;
    public int fourier_iteration;

    // method
    public void setAttribute(int sampling_freqency, int detect_freqency, int n, int fourier_batch_size);
    private int getApproximateFrequencyIndex(double detect_frequency);
    private double takeAverageAround(Signal signal, int detect_index);
    private boolean isSteep(Signal signal, int detect_index, double average_frequency);
    public boolean isDetectSteepIndex(Signal signal, double detect_frequency);
    }
```

* フーリエ変換された周波数領域に置き換えられた信号がnodeから送信された信号(`detecte_signal`)を含んでいるのかを検出するmethodを持つ

## InterfaceDistance

|method name|description|
|--|--|
|getDistance|サンプリング周波数と推定されたindexからnode間の距離を返す|

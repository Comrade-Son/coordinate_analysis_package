# coordinate_analysis_package

## abstruct

* host <= client 、client <=> client で音波を送信して、信号情報を全てhostに集約(これは扱わない)
* 信号情報から各node間の距離を推定
* 各node間の距離から座標を推定

## package

`node_information`

* node間の情報、node自体の情報を扱う

`signal_analysis`

* 信号データから距離を推定する

## result of sample-code

### value of coordinate

![image](https://user-images.githubusercontent.com/57125746/144279231-6e3ad965-b29b-47dc-89a1-8e6cab979af4.png)

### plot

|青色|赤色|
|--|--|
|実測値|推定値|

![image (2)](https://user-images.githubusercontent.com/57125746/144277854-7307b263-1af4-410e-9f89-d4582ed69f2c.png)

plot: https://www.geogebra.org/graphing?lang=ja

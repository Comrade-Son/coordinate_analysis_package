# coodinate_analysis_package

## abstruct

* host <= client 、client <=> client で音波を送信して、信号情報を全てhostに集約(これは扱わない)
* 信号情報から各node間の距離を推定
* 各node間の距離から座標を推定

## package

`node_information`

* node間の情報、node自体の情報を扱う

`signal_analysis`

* 信号データから距離を推定する

# ChargingView
[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/ChargingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/ChargingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

<br>

  * [中文](#%E4%B8%AD%E6%96%87)
  * [English](#english)
  * [License](#license)

<br>

![guf](https://github.com/samlss/ChargingView/blob/master/screenshot/screenshot1.gif)


## 中文
一个简单的充电view<br>

### 使用<br>
在根目录的build.gradle添加这一句代码：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```
dependencies {
    implementation 'com.github.samlss:ChargingView:1.0'
}
```

布局中使用：
```
<com.iigo.library.ChargingView
            android:id="@+id/cv2"
            android:layout_marginTop="10dp"
            android:layout_width="100dp"
            android:layout_height="200dp"
            app:progress="50"
            app:progressTextColor="@android:color/black"
            app:chargingColor="@android:color/holo_red_light"
            app:bg_color="#eeeeee"
            app:progressTextSize="20dp" />
```

<br>

代码中使用：
```
  chargingView.setProgress(95);
  chargingView.setBgColor(Color.parseColor("#aaaaaa"));
  chargingView.setChargingColor(Color.YELLOW);
  chargingView.setTextColor(Color.RED);
  chargingView.setTextSize(25);
```

<br>

属性说明：

| 属性        | 说明           |
| ------------- |:-------------:|
| bg_color      | 背景颜色 |
| chargingColor | 充电中颜色 |
| progress      | 当前进度0-100 |
| progressTextSize      | 显示进度text大小|
| progressTextColor      | 显示进度text颜色 |

<br>

## English
A simple charging view.

### Use<br>
Add it in your root build.gradle at the end of repositories：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```
dependencies {
    implementation 'com.github.samlss:ChargingView:1.0'
}
```


in layout.xml：
```
<com.iigo.library.ChargingView
            android:id="@+id/cv2"
            android:layout_marginTop="10dp"
            android:layout_width="100dp"
            android:layout_height="200dp"
            app:progress="50"
            app:progressTextColor="@android:color/black"
            app:chargingColor="@android:color/holo_red_light"
            app:bg_color="#eeeeee"
            app:progressTextSize="20dp" />
```

<br>

in java code：
```
  chargingView.setProgress(95);
  chargingView.setBgColor(Color.parseColor("#aaaaaa"));
  chargingView.setChargingColor(Color.YELLOW);
  chargingView.setTextColor(Color.RED);
  chargingView.setTextSize(25);
```

<br>

Attributes description：

| attr        | d           |
| ------------- |:-------------:|
| bg_color      | the background color |
| chargingColor | the charging color |
| progress      | current progress:0-100 |
| progressTextSize      | the progress text size|
| progressTextColor      | the progress text color |




## License
Copyright (c) 2018 samlss

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

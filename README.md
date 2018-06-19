# Swipe Screen Behavior

A CoordinatorLayout Behavior for implementing swipe screen transition behavior.

![screenshot](screenshots/swipescreenbehavior.gif)

## Usage

This CoordinatorLayout Behavior can be added either through XML or code.

#### XML

To add this Behavior, add the following line to the direct child of the CoordinatorLayout:

```
app:layout_behavior="@string/swipe_behavior"
```

#### Code
```
val layoutParams = constraintLayout.layoutParams as CoordinatorLayout.LayoutParams
layoutParams.behavior = SwipeScreenBehavior<ConstraintLayout>()
```

Then to get a callback when the screen needs to be dismissed add the below code:

```
(layoutParams.behavior as SwipeScreenBehavior).setOnDismissListener(object : SwipeCallback {
    override fun dismissListener(parent: View?) {
        exitActivity(parent)
    }
})
```

## Usage

#### Maven

```
<dependency>
  <groupId>com.sjthn.swipeablescreens</groupId>
  <artifactId>swipeablescreens</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

#### Gradle

```
implementation 'com.sjthn.swipeablescreens:swipeablescreens:1.0.0'
```

## Sample
Checkout the [sample project](https://github.com/sjthn/SwipeScreenBehavior/tree/master/example) for how this works.

## License
```
MIT License

Copyright (c) 2018 Srijith
```

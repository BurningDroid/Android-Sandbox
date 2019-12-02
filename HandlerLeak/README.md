### Handler Leak

This is a sample proejct for the Handler Leak.

Below code has a memory leak issue.

```kotlin
class LeakActivity : AppCompatActivity() {

    private val TAG = LeakActivity::class.java.simpleName

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handleMessage")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)

        handler.postDelayed({
            Log.d(TAG, "run!!!")
        }, 10_000)
        finish()
    }
}
```

What issue? and Why?

If you want to see more details, please visit [here](http://youknow.kim/2019/12/02/android-handler-leak/)

package com.unmsm.videocall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import java.util.UUID


class CallActivity : AppCompatActivity() {

    var username = ""
    var friendsUsername = ""

    var isPeerConnected = false

var firebaseRef = Firebase.database.getReference("users")

    var isAudio = true
    var isVideo = true
    var uniqueId = ""

    private lateinit var webView: WebView
    private lateinit var callLayout: RelativeLayout
    private lateinit var incomingCallTxt: TextView
    private lateinit var acceptBtn: ImageView
    private lateinit var rejectBtn: ImageView
    private lateinit var inputLayout: RelativeLayout
    private lateinit var callControlLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        //Recupero el nombre colocado de la ventana anterior
        username = intent.getStringExtra("username")!!

        val callBtn : Button =findViewById(R.id.callBtn)
        val friendNameEdit : EditText = findViewById(R.id.friendNameEdit)
        val toggleAudioBtn : ImageView =findViewById(R.id.toggleAudioBtn)
        val toggleVideoBtn : ImageView =findViewById(R.id.toggleVideoBtn)

        webView = findViewById(R.id.webView)
        callLayout = findViewById(R.id.callLayout)
        incomingCallTxt = findViewById(R.id.incomingCallTxt)
        acceptBtn = findViewById(R.id.acceptBtn)
        rejectBtn = findViewById(R.id.rejectBtn)
        inputLayout = findViewById(R.id.inputLayout)
        callControlLayout = findViewById(R.id.callControlLayout)





        callBtn.setOnClickListener {
            friendsUsername = friendNameEdit.text.toString()
            sendCallRequest()
        }

        //Activar microfono o apagar microfono
        toggleAudioBtn.setOnClickListener {
            isAudio = !isAudio
            callJavascriptFunction("javascript:toggleAudio(\"${isAudio}\")")
            toggleAudioBtn.setImageResource(if (isAudio) R.drawable.ic_baseline_mic_24 else R.drawable.ic_baseline_mic_off_24 )
        }

        //Activar camara o apagar camara
        toggleVideoBtn.setOnClickListener {
            isVideo = !isVideo
            callJavascriptFunction("javascript:toggleVideo(\"${isVideo}\")")
            toggleVideoBtn.setImageResource(if (isVideo) R.drawable.ic_baseline_videocam_24 else R.drawable.ic_baseline_videocam_off_24 )
        }
        setupWebView()
    }



    private fun sendCallRequest() {
        if (!isPeerConnected) {
            // este parece ser el problema que tengo con el internet
            Toast.makeText(this, "No estas conectado a internet, revisa tu conexión", Toast.LENGTH_LONG).show()
            return
        }

        firebaseRef.child(friendsUsername).child("incoming").setValue(username)
        firebaseRef.child(friendsUsername).child("isAvailable").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.value.toString() == "true") {
                    listenForConnId()
                }
            }
        })

    }

    private fun listenForConnId() {
        firebaseRef.child(friendsUsername).child("connId").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null)
                    return
                switchToControls()
                callJavascriptFunction("javascript:startCall(\"${snapshot.value}\")")
            }

        })
    }

    private fun setupWebView() {

        webView.webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.addJavascriptInterface(JavascriptInterface(this), "Android")

        loadVideoCall()
    }

    private fun loadVideoCall() {
        //Llamamos al codigo HTMl para cargar el video
        val filePath = "file:android_asset/call.html"
        webView.loadUrl(filePath)

        webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                initializePeer()
            }
        }
    }



    private fun initializePeer() {

        uniqueId = getUniqueID()
        //Esta es la función de inicio en el servidor el init
        callJavascriptFunction("javascript:init(\"${uniqueId}\")")
        firebaseRef.child(username).child("incoming").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                onCallRequest(snapshot.value as? String)
            }

        })

    }




    private fun onCallRequest(caller: String?) {
        if (caller == null) return

        callLayout.visibility = View.VISIBLE
        incomingCallTxt.text = "$caller is calling..."

        //enviamos a la base de datos los valor de coonI
        acceptBtn.setOnClickListener {
            firebaseRef.child(username).child("connId").setValue(uniqueId)
            firebaseRef.child(username).child("isAvailable").setValue(true)

            callLayout.visibility = View.GONE
            switchToControls()
        }

        rejectBtn.setOnClickListener {
            // El valor de incoming lo regresa a null
            firebaseRef.child(username).child("incoming").setValue(null)
            callLayout.visibility = View.GONE
        }

    }

    private fun switchToControls() {
        inputLayout.visibility = View.GONE
        callControlLayout.visibility = View.VISIBLE
    }
    //Creamos el UUID random
    private fun getUniqueID(): String {
        return UUID.randomUUID().toString()
    }

    private fun callJavascriptFunction(functionString: String) {
        webView.post { webView.evaluateJavascript(functionString, null) }
    }

    fun onPeerConnected() {
        isPeerConnected = true
    }


    override fun onDestroy() {
        firebaseRef.child(username).setValue(null)
        webView.loadUrl("about:blank")
        super.onDestroy()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



}
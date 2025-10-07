class CameraHelper(
    private val context: Context,
    private val surfaceTextureListener: TextureView.SurfaceTextureListener
) {
    private var cameraDevice: CameraDevice? = null
    private lateinit var captureSession: CameraCaptureSession

    fun openCamera(textureView: TextureView) {
        val manager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = manager.cameraIdList[0] // back camera
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        manager.openCamera(cameraId, object : CameraDevice.StateCallback() {
            override fun onOpened(device: CameraDevice) {
                cameraDevice = device
                val surface = Surface(textureView.surfaceTexture)
                val requestBuilder = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                requestBuilder.addTarget(surface)
                device.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        captureSession = session
                        requestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
                        session.setRepeatingRequest(requestBuilder.build(), null, null)
                    }
                    override fun onConfigureFailed(session: CameraCaptureSession) {}
                }, null)
            }
            override fun onDisconnected(device: CameraDevice) {}
            override fun onError(device: CameraDevice, error: Int) {}
        }, null)
    }
}

Java.perform(function () {
    try {
        var Intent = Java.use('android.content.Intent');
        var Application = Java.use('android.app.Application');
        var MyBroadCastReceiver = Java.use('com.android.insecurebankv2.MyBroadCastReceiver');
        var SmsManager = Java.use('android.telephony.SmsManager');
        var intent = Intent.$new();
        intent.setAction('theBroadcast');
        intent.putExtra('message', 'Hello from Frida!');
        var currentApplication = Java.use('android.app.ActivityThread').currentApplication();
        var context = currentApplication.getApplicationContext();
        var smsManager = SmsManager.getDefault();
        var phoneNumber = '12345';
        var message = 'This is a test message from Frida.';
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        console.log('SMS sent successfully');
        context.sendBroadcast(intent);
        console.log('Broadcast triggered successfully');
    } catch (error) {
        console.error('Error in Frida script:', error);
    }
    
});

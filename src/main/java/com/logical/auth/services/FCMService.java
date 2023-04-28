//package com.logical.auth.services;
//
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//import com.logical.auth.model.Request.PushNotificationRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FCMService {
//    private Logger logger = LoggerFactory.getLogger(FCMService.class);
//    public void sendMessageToToken(PushNotificationRequest request)
//            throws InterruptedException, ExecutionException {
//        Message message = getPreconfiguredMessageToToken(request);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String jsonOutput = gson.toJson(message);
//        String response = sendAndGetResponse(message);
//        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
//    }
//    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
//        return FirebaseMessaging.getInstance().sendAsync(message).get();
//    }
//    private AndroidConfig getAndroidConfig(String topic) {
//        return AndroidConfig.builder()
//                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
//                .setPriority(AndroidConfig.Priority.HIGH)
//                .setNotification(AndroidNotification.builder()
//                        .setTag(topic).build()).build();
//    }
//    private ApnsConfig getApnsConfig(String topic) {
//        return ApnsConfig.builder()
//                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
//    }
//    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
//                .build();
//    }
//    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).setTopic(request.getMessage())
//                .build();
//    }
//    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
//                .build();
//    }
//    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
//        AndroidConfig androidConfig = getAndroidConfig(request.getMessage());
//        ApnsConfig apnsConfig = getApnsConfig(request.getMessage());
//        return Message.builder()
//                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
//                        new Notification(request.getMessage(), request.getMessage()));
//    }
//}
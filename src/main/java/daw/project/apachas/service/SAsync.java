package daw.project.apachas.service;

import daw.project.apachas.converter.ConUser;
import daw.project.apachas.model.MEmailBody;
import daw.project.apachas.model.MUser;
import daw.project.apachas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.List;

@Service
@Async
public class SAsync {
    @Autowired
    @Qualifier("RUserEvent")
    private RUserEvent rUserEvent;

    @Autowired
    @Qualifier("RUserUserEvent")
    private RUserUserEvent rUserUserEvent;

    @Autowired
    @Qualifier("RUserUser")
    private RUserUser rUserUser;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    @Autowired
    @Qualifier("SEmail")
    SEmail sEmail;

    @Autowired
    @Qualifier("RGroupUser")
    private RGroupUser rGroupUser;

    //HOY SE TE HA AÑADIDO A UN EVENTO (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void eventNotification(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());

        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);

        List<MUser> mUserList = conUser.conUserList(rUserEvent.findAddedNewEvent(currentDate, previousDate));

        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventNotification.html");
        }
    }

    //RECORDATORIO DE QUE SE APROXIMA EVENTO (1 vez al día)
    @Scheduled(cron = "0 0 8 ? * *")
    public void eventReminder(){
        //una vez al dia la fecha actual + 3 días sin tener en cuenta la hora
        Timestamp date = new Timestamp(System.currentTimeMillis() + 259200000L);
        List<MUser> mUserList = conUser.conUserList(rUserEvent.findNearEvent(date));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventReminder.html");
        }
    }

    //RECORDATORIO DE QUE EMPIEZA EVENTO (1 vez al día)
    @Scheduled(cron = "0 0 8 ? * *")
    public void eventStart(){
        //una vez al día la fecha actual sin contar las horas
        Timestamp date = new Timestamp(System.currentTimeMillis());
        List<MUser> mUserList = conUser.conUserList(rUserEvent.findEventStart(date));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventStart.html");
        }
    }

    //RECORDATORIO DE QUE SE HA CERRADO UN EVENTO (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void eventFinished(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);
        List<MUser> mUserList = conUser.conUserList(rUserEvent.findEventFinished(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/eventFinished.html");
        }
    }

    //RECORDATORIO DE QUE SE TE HA AÑADIDO A UN GRUPO (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void groupNotification(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);
        List<MUser> mUserList = conUser.conUserList(rGroupUser.findAddedNewGroup(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/groupNotification.html");
        }
    }

    //RECORDATORIO DE SOLICITUD DE AMISTAD (cada 3 horas)
    @Scheduled(cron = " 0 0 0/3 * * ?")
    public void userFriendRequest(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 7200000L);
        List<MUser> mUserList = conUser.conUserList(rUserUser.findNewRequest(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/userFriendRequest.html");
        }
    }

    //RECORDATORIO DE TRANSACCIONES POR PAGAR (cada mes)
    @Scheduled(cron = "0 0 8 1 * ?")
    public void transactionPayReminder(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 2592000000L);
        //cada mes sin tener en cuenta las horas entre el día actual y 30 días antes
        List<MUser> mUserList = conUser.conUserList(rUserUserEvent.findPendingPay(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/transactionPayReminder.html");
        }
    }

    //RECORDATORIO DE TRANSACCIONES POR CONFIRMAR (cada mes)
    @Scheduled(cron = "0 0 8 1 * ?")
    public void transactionConfirmReminder(){
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Timestamp previousDate = new Timestamp(currentDate.getTime() - 2592000000L);
        //cada mes sin tener en cuenta las horas entre el día actual y 30 días antes
        List<MUser> mUserList = conUser.conUserList(rUserUserEvent.findPendingConfirm(currentDate, previousDate));
        for (MUser mUser: mUserList) {
            sendNotificationEmail(mUser, "src/main/resources/templates/transactionConfirmReminder.html");
        }
    }

    private boolean sendNotificationEmail(MUser mUser, String urlTemplate){
        File file = null;
        String line;
        FileReader fr = null;
        BufferedReader br = null;
        String content = "";
        try {
            file = new File (urlTemplate);
            fr = new FileReader (file);
            br = new BufferedReader(fr);
            while((line=br.readLine())!=null){
                content += line;
            }
            content = content
                    .replace(" * ",  "cid:image001");
            boolean send = sEmail.sendEmailWithImage(new MEmailBody(content, mUser.getUserEmail(),"APACHAS: Notificación"));
            if (send){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}

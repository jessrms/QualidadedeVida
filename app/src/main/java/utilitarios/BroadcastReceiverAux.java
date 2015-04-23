package utilitarios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import appcozinha.com.br.qualidadedevida.R;

/**
 * Created by Peterson on 20/03/2015.
 */
public class BroadcastReceiverAux extends BroadcastReceiver {
    public static final String PREFS_DICAS = "Dicas";
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service1 = new Intent("SERVICO_TEST");
        context.startService(service1);

        Intent service2 = new Intent("SERVICO_EMPRESA");
        context.startService(service2);

        // String dicas=null;
        // MyAlarmService dicas=new MyAlarmService();
        //dicas.worldlist.toString();
        Log.i("Script", "->  chamou Alarmeservice e o Empresaservice!");
        //if (MyAlarmService.!= null) {

            //gerarNotificacao(context, new Intent(context, Dicas2.class), "Novas dicas do SESI", "QUALIDADE DE VIDA", "Dicas SESI");
        //}else{
          //  Log.i("Script","-> sem dados ");
        //}
    }

    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.drawable.ic_launcher, n);

     /*   try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception e){}*/
    }
}

package projetandroid.entmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.AlertDialog;

public class Connexion extends ActionBarActivity implements View.OnClickListener{

    private Button boutonCo1;
    private Button boutonCo2;
    private Button boutonCancel;
    private ImageButton boutonHelp;
    private LinearLayout identification;
    private Animation anim_boutonCo1_show;
    private Animation anim_boutonCo1_hide;
    private Animation anim_identification_show;
    private Animation anim_identification_hide;
    private Animation anim_boutonHelp;
    private AlertDialog.Builder alertPwdBuilder;
    private AlertDialog alertPwd;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //Définition des éléments de la page
        boutonCo1 = (Button)findViewById(R.id.boutonCo1);
        boutonCo2 = (Button)findViewById(R.id.boutonCo2);
        boutonCancel = (Button)findViewById(R.id.boutonCancel);
        boutonHelp = (ImageButton)findViewById(R.id.boutonHelp);
        identification = (LinearLayout)findViewById(R.id.identification);
        context = this;

        //Définition des animations
        anim_boutonCo1_hide = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
        anim_boutonCo1_show = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        anim_identification_show = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
        anim_identification_hide = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        anim_boutonHelp = AnimationUtils.loadAnimation(this, R.anim.scale_from_corners);

        //Définition des listeners
        boutonCo1.setOnClickListener(this);
        boutonCancel.setOnClickListener(this);
        boutonCo2.setOnClickListener(this);
        boutonHelp.setOnClickListener(this);
        initAnim_boutonHelp();

        //Définition de la boîte de dialogue
        alertPwdBuilder = new AlertDialog.Builder(context);
        initAlertPwdBuilder();
        alertPwd = alertPwdBuilder.create();
    }

    //Gestion des évènements sur clic des boutons
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boutonCo1:
                boutonCo1.startAnimation(anim_boutonCo1_hide);
                boutonCo1.setVisibility(View.INVISIBLE);
                identification.startAnimation(anim_identification_show);
                identification.setVisibility(View.VISIBLE);
                break;

            case R.id.boutonCo2:
                /* Connexion de l'utilisateur à l'application et ouverture de la page d'accueil */
                Intent intent = new Intent(Connexion.this, Accueil.class);
                startActivity(intent);
                /* TODO */
                /* Définir la connexion de l'utilisateur à l'application */
                Toast.makeText(getApplicationContext(), R.string.boutonCo2Toast, Toast.LENGTH_SHORT).show();
                break;

            case R.id.boutonCancel:
                identification.startAnimation(anim_identification_hide);
                identification.setVisibility(View.INVISIBLE);
                boutonCo1.startAnimation(anim_boutonCo1_show);
                boutonCo1.setVisibility(View.VISIBLE);
                break;

            case R.id.boutonHelp:
                boutonHelp.startAnimation(anim_boutonHelp);
                break;
        }
    }

    //Définition du contenu de la boîte de dialogue
    private void initAlertPwdBuilder() {
        alertPwdBuilder.setTitle(R.string.alertPwdTitle);
        alertPwdBuilder.setMessage(R.string.alertPwdMessage);
        alertPwdBuilder.setIcon(R.drawable.ic_action_help);

        alertPwdBuilder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /* TODO */
                /*Code pour redéfinir un mot de passe aléatoire et le renvoyer par mail*/
                Toast.makeText(getApplicationContext(), R.string.alertPwdToast, Toast.LENGTH_SHORT).show();
            }
        });
        alertPwdBuilder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    //Définition du listener de l'animation du boutonHelp
    private void initAnim_boutonHelp() {
        anim_boutonHelp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                alertPwd.show();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}

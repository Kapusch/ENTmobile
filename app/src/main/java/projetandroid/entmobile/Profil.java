package projetandroid.entmobile;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Profil extends ActionBarActivity implements View.OnClickListener{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private ImageButton boutonCancelAdresse;
    private ImageButton boutonCancelTelephone;
    private ImageButton boutonCancelEmail;
    private ImageButton boutonCancelSiteWeb;
    private ImageButton boutonValidateAdresse;
    private ImageButton boutonValidateTelephone;
    private ImageButton boutonValidateEmail;
    private ImageButton boutonValidateSiteWeb;
    private Animation anim_boutonCancel;
    private Animation anim_boutonValidate;
    private Button boutonAccesListeUE;
    private EditText adresse_profil_val;
    private EditText email_profil_val;
    private EditText telephone_profil_val;
    private EditText site_web_profil_val;
    private String new_text;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Définition des éléments de la page
        boutonCancelAdresse = (ImageButton)findViewById(R.id.adresse_profil_cancel);
        boutonCancelTelephone = (ImageButton)findViewById(R.id.telephone_profil_cancel);
        boutonCancelEmail = (ImageButton)findViewById(R.id.email_profil_cancel);
        boutonCancelSiteWeb = (ImageButton)findViewById(R.id.site_web_profil_cancel);
        boutonValidateAdresse = (ImageButton)findViewById(R.id.adresse_profil_validate);
        boutonValidateTelephone = (ImageButton)findViewById(R.id.telephone_profil_validate);
        boutonValidateEmail = (ImageButton)findViewById(R.id.email_profil_validate);
        boutonValidateSiteWeb = (ImageButton)findViewById(R.id.site_web_profil_validate);
        boutonAccesListeUE = (Button)findViewById(R.id.boutonAccesListeUE);
        adresse_profil_val = (EditText)findViewById(R.id.adresse_profil_val);
        email_profil_val = (EditText)findViewById(R.id.email_profil_val);
        telephone_profil_val = (EditText)findViewById(R.id.telephone_profil_val);
        site_web_profil_val = (EditText)findViewById(R.id.site_web_profil_val);

        /*TODO*/
        //Créer une fonction pour remplir les éléments du profil depuis la BDD (avec les id du layout)
        //set_tab_profil();

        //Définition des animations des boutons
        anim_boutonCancel = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        anim_boutonValidate = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        //Définition des listeners
        boutonCancelAdresse.setOnClickListener(this);
        boutonCancelTelephone.setOnClickListener(this);
        boutonCancelEmail.setOnClickListener(this);
        boutonCancelSiteWeb.setOnClickListener(this);
        boutonValidateAdresse.setOnClickListener(this);
        boutonValidateTelephone.setOnClickListener(this);
        boutonValidateEmail.setOnClickListener(this);
        boutonValidateSiteWeb.setOnClickListener(this);
        boutonAccesListeUE.setOnClickListener(this);

        //Définition des titres
        titleBar = getResources().getString(R.string.label_profil);
        titleMenu = getResources().getString(R.string.label_menu);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_profil);
        drawerListView = (ListView) findViewById(R.id.left_drawer_profil);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_profil);

        // Définition de la gestion d'ouverture/fermeture du menu sur glisser du doigt
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            //Menu fermé : gestion du titre de l'activité
            public void onDrawerClosed(View view){
                getSupportActionBar().setTitle(titleBar);
            }
            //Menu ouvert : gestion du titre du menu
            public void onDrawerOpened(View view){
                getSupportActionBar().setTitle(titleMenu);
            }
        };

        // Définition du listener du menu
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Définition d'un bouton pour ouvrir le menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Gestion du clic sur un item du menu
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    // Déclenchement du menu sur clic du bouton Home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Afficher l'icône du bouton du menu
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    // Implémentation de la gestion du clic sur un item
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            /* Ce qu'il se passe quand on clique sur un item du menu */
            switch (position){
                case 0:
                    intent = new Intent(Profil.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(Profil.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(Profil.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(Profil.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(Profil.this, Notes.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(Profil.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    //Gestion des évènements sur clic des boutons
    /* TODO */
    /* Définir dans la BDD les actions des clics sur les boutons Cancel, Validate */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adresse_profil_cancel:
                boutonCancelAdresse.startAnimation(anim_boutonCancel);
                adresse_profil_val.setText(getResources().getString(R.string.vide));
                Toast.makeText(getApplicationContext(), "Modification annulée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.adresse_profil_validate:
                boutonValidateAdresse.startAnimation(anim_boutonValidate);
                new_text = adresse_profil_val.getText().toString();
                adresse_profil_val.setText(getResources().getString(R.string.vide));
                adresse_profil_val.setHint(new_text);
                Toast.makeText(getApplicationContext(), "Modification validée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.telephone_profil_cancel:
                boutonCancelTelephone.startAnimation(anim_boutonCancel);
                telephone_profil_val.setText(getResources().getString(R.string.vide));
                Toast.makeText(getApplicationContext(), "Modification annulée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.telephone_profil_validate:
                boutonValidateTelephone.startAnimation(anim_boutonValidate);
                new_text = telephone_profil_val.getText().toString();
                telephone_profil_val.setText(getResources().getString(R.string.vide));
                telephone_profil_val.setHint(new_text);
                Toast.makeText(getApplicationContext(), "Modification validée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.email_profil_cancel:
                boutonCancelEmail.startAnimation(anim_boutonCancel);
                email_profil_val.setText(getResources().getString(R.string.vide));
                Toast.makeText(getApplicationContext(), "Modification annulée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.email_profil_validate:
                boutonValidateEmail.startAnimation(anim_boutonValidate);
                new_text = email_profil_val.getText().toString();
                email_profil_val.setText(getResources().getString(R.string.vide));
                email_profil_val.setHint(new_text);
                Toast.makeText(getApplicationContext(), "Modification validée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.site_web_profil_cancel:
                boutonCancelSiteWeb.startAnimation(anim_boutonCancel);
                site_web_profil_val.setText(getResources().getString(R.string.vide));
                Toast.makeText(getApplicationContext(), "Modification annulée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.site_web_profil_validate:
                boutonValidateSiteWeb.startAnimation(anim_boutonValidate);
                new_text = site_web_profil_val.getText().toString();
                site_web_profil_val.setText(getResources().getString(R.string.vide));
                site_web_profil_val.setHint(new_text);
                Toast.makeText(getApplicationContext(), "Modification validée", Toast.LENGTH_SHORT).show();
                break;
            case R.id.boutonAccesListeUE:
                Intent intent = new Intent(Profil.this, ListeUE.class);
                startActivity(intent);
                break;
        }
    }
}

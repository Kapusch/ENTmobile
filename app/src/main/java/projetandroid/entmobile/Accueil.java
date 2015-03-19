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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Accueil extends ActionBarActivity{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Calendar semaine_actu;
    Intent intent;
    private GridLayout planning;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //Définition de la semaine en cours
        setSemaineActu();


        //Définition des titres
        titleBar = getResources().getString(R.string.label_accueil);
        titleMenu = getResources().getString(R.string.label_menu);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_accueil);
        drawerListView = (ListView) findViewById(R.id.left_drawer_accueil);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_accueil);

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

    //Récupération et édit de la semaine en cours
    private void setSemaineActu() {
        //Récupération de la semaine
        planning = (GridLayout)findViewById(R.id.tableau1);
        semaine_actu = semaine_actu.getInstance();
        int week_actu_number = semaine_actu.get(Calendar.WEEK_OF_YEAR);
        int year_actu_number = semaine_actu.get(Calendar.YEAR);
        //Ecrire le mois seulement dans la barre d'action à côté du bouton calendrier
        //SimpleDateFormat month_format = new SimpleDateFormat("MMMM");
        //String month_name = month_format.format(semaine_actu.getTime()).toUpperCase();
        String[] days = {getResources().getString(R.string.lundi), getResources().getString(R.string.mardi), getResources().getString(R.string.mercredi), getResources().getString(R.string.jeudi), getResources().getString(R.string.vendredi), getResources().getString(R.string.samedi)};
        TextView cell_temp = (TextView)planning.getChildAt(0);
        String date_temp = "Semaine n°"+String.valueOf(semaine_actu.get(Calendar.WEEK_OF_YEAR));
        cell_temp.setText(date_temp);

        //Récupération des jours de la semaine
        semaine_actu.clear();
        semaine_actu.set(Calendar.WEEK_OF_YEAR, week_actu_number);
        semaine_actu.set(Calendar.YEAR, year_actu_number);SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date startDate;
        startDate = semaine_actu.getTime();
        String startDateText = formatter.format(startDate);
        int startDateInt;
        for(int i = 1; i < 7; i++){
            cell_temp = (TextView) planning.getChildAt(i);
            startDateInt = Integer.valueOf(startDateText)+(i-1);
            date_temp = days[(i-1)].concat(" "+startDateInt);
            cell_temp.setText(date_temp);
        }
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
                    intent = new Intent(Accueil.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(Accueil.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(Accueil.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(Accueil.this, Notes.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(Accueil.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(Accueil.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}

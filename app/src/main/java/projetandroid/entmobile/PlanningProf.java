package projetandroid.entmobile;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlanningProf extends ActionBarActivity{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    Intent intent;
    private TextView jlh8, jlh10, jlh13, jlh15,jlh17, jmh8, jmh10, jmh13, jmh15, jmh17, jmeh8, jmeh10, jmeh13, jmeh15, jmeh17;
    private TextView jjh8, jjh10, jjh13, jjh15, jjh17, jvh8, jvh10, jvh13, jvh15, jvh17, jsh8, jsh10, jsh13, jsh15, jsh17;
    private ImageButton removejlh8, removejlh10, removejlh13, removejlh15,removejlh17, removejmh8, removejmh10, removejmh13, removejmh15, removejmh17, removejmeh8, removejmeh10, removejmeh13, removejmeh15, removejmeh17;
    private ImageButton removejjh8, removejjh10, removejjh13, removejjh15, removejjh17, removejvh8, removejvh10, removejvh13, removejvh15, removejvh17, removejsh8, removejsh10, removejsh13, removejsh15, removejsh17;
    private ImageButton forwardjlh8, forwardjlh10, forwardjlh13, forwardjlh15,forwardjlh17, forwardjmh8, forwardjmh10, forwardjmh13, forwardjmh15, forwardjmh17, forwardjmeh8, forwardjmeh10, forwardjmeh13, forwardjmeh15, forwardjmeh17;
    private ImageButton forwardjjh8, forwardjjh10, forwardjjh13, forwardjjh15, forwardjjh17, forwardjvh8, forwardjvh10, forwardjvh13, forwardjvh15, forwardjvh17, forwardjsh8, forwardjsh10, forwardjsh13, forwardjsh15, forwardjsh17;
    private Context context;
    private Animation clique;
    private TextView cellule[]={jlh8, jlh10, jlh13, jlh15,jlh17, jmh8, jmh10, jmh13, jmh15, jmh17, jmeh8, jmeh10, jmeh13, jmeh15, jmeh17, jjh8, jjh10, jjh13, jjh15, jjh17, jvh8, jvh10, jvh13, jvh15, jvh17, jsh8, jsh10, jsh13, jsh15, jsh17};
    private ImageButton remove[]={removejlh8, removejlh10, removejlh13, removejlh15,removejlh17, removejmh8, removejmh10, removejmh13, removejmh15, removejmh17, removejmeh8, removejmeh10, removejmeh13, removejmeh15, removejmeh17,removejjh8, removejjh10, removejjh13, removejjh15, removejjh17, removejvh8, removejvh10, removejvh13, removejvh15, removejvh17, removejsh8, removejsh10, removejsh13, removejsh15, removejsh17};
    private ImageButton forward[]={forwardjlh8, forwardjlh10, forwardjlh13, forwardjlh15,forwardjlh17, forwardjmh8, forwardjmh10, forwardjmh13, forwardjmh15, forwardjmh17, forwardjmeh8, forwardjmeh10, forwardjmeh13, forwardjmeh15, forwardjmeh17,forwardjjh8, forwardjjh10, forwardjjh13, forwardjjh15, forwardjjh17, forwardjvh8, forwardjvh10, forwardjvh13, forwardjvh15, forwardjvh17, forwardjsh8, forwardjsh10, forwardjsh13, forwardjsh15, forwardjsh17};
    private int i;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_prof);

        jlh8 = (TextView)findViewById(R.id.jlh8);
        jlh10 = (TextView)findViewById(R.id.jlh10);
        jlh13 = (TextView)findViewById(R.id.jlh13);
        jlh15 = (TextView)findViewById(R.id.jlh15);
        jlh17 = (TextView)findViewById(R.id.jlh17);
        jmh8 = (TextView)findViewById(R.id.jmh8);
        jmh10 = (TextView)findViewById(R.id.jmh10);
        jmh13 = (TextView)findViewById(R.id.jmh13);
        jmh15 = (TextView)findViewById(R.id.jmh15);
        jmh17 = (TextView)findViewById(R.id.jmh17);
        jmeh8 = (TextView)findViewById(R.id.jmeh8);
        jmeh10 = (TextView)findViewById(R.id.jmeh10);
        jmeh13 = (TextView)findViewById(R.id.jmeh13);
        jmeh15 = (TextView)findViewById(R.id.jmeh15);
        jmeh17 = (TextView)findViewById(R.id.jmeh17);
        jjh8 = (TextView)findViewById(R.id.jjh8);
        jjh10 = (TextView)findViewById(R.id.jjh10);
        jjh13 = (TextView)findViewById(R.id.jjh13);
        jjh15 = (TextView)findViewById(R.id.jjh15);
        jjh17 = (TextView)findViewById(R.id.jjh17);
        jvh8 = (TextView)findViewById(R.id.jvh8);
        jvh10 = (TextView)findViewById(R.id.jvh10);
        jvh13 = (TextView)findViewById(R.id.jvh13);
        jvh15 = (TextView)findViewById(R.id.jvh15);
        jvh17 = (TextView)findViewById(R.id.jvh17);
        jsh8 = (TextView)findViewById(R.id.jsh8);
        jsh10 = (TextView)findViewById(R.id.jsh10);
        jsh13 = (TextView)findViewById(R.id.jsh13);
        jsh15 = (TextView)findViewById(R.id.jsh15);
        jsh17 = (TextView)findViewById(R.id.jsh17);

        removejlh8 = (ImageButton)findViewById(R.id.removejlh8);
        removejlh10 = (ImageButton)findViewById(R.id.removejlh10);
        removejlh13 = (ImageButton)findViewById(R.id.removejlh13);
        removejlh15 = (ImageButton)findViewById(R.id.removejlh15);
        removejlh17 = (ImageButton)findViewById(R.id.removejlh17);
        removejmh8 = (ImageButton)findViewById(R.id.removejmh8);
        removejmh10 = (ImageButton)findViewById(R.id.removejmh10);
        removejmh13 = (ImageButton)findViewById(R.id.removejmh13);
        removejmh15 = (ImageButton)findViewById(R.id.removejmh15);
        removejmh17 = (ImageButton)findViewById(R.id.removejmh17);
        removejmeh8 = (ImageButton)findViewById(R.id.removejmeh8);
        removejmeh10 = (ImageButton)findViewById(R.id.removejmeh10);
        removejmeh13 = (ImageButton)findViewById(R.id.removejmeh13);
        removejmeh15 = (ImageButton)findViewById(R.id.removejmeh15);
        removejmeh17 = (ImageButton)findViewById(R.id.removejmeh17);
        removejjh8 = (ImageButton)findViewById(R.id.removejjh8);
        removejjh10 = (ImageButton)findViewById(R.id.removejjh10);
        removejjh13 = (ImageButton)findViewById(R.id.removejjh13);
        removejjh15 = (ImageButton)findViewById(R.id.removejjh15);
        removejjh17 = (ImageButton)findViewById(R.id.removejjh17);
        removejvh8 = (ImageButton)findViewById(R.id.removejvh8);
        removejvh10 = (ImageButton)findViewById(R.id.removejvh10);
        removejvh13 = (ImageButton)findViewById(R.id.removejvh13);
        removejvh15 = (ImageButton)findViewById(R.id.removejvh15);
        removejvh17 = (ImageButton)findViewById(R.id.removejvh17);
        removejsh8 = (ImageButton)findViewById(R.id.removejsh8);
        removejsh10 = (ImageButton)findViewById(R.id.removejsh10);
        removejsh13 = (ImageButton)findViewById(R.id.removejsh13);
        removejsh15 = (ImageButton)findViewById(R.id.removejsh15);
        removejsh17 = (ImageButton)findViewById(R.id.removejsh17);

        forwardjlh8 = (ImageButton)findViewById(R.id.forwardjlh8);
        forwardjlh10 = (ImageButton)findViewById(R.id.forwardjlh10);
        forwardjlh13 = (ImageButton)findViewById(R.id.forwardjlh13);
        forwardjlh15 = (ImageButton)findViewById(R.id.forwardjlh15);
        forwardjlh17 = (ImageButton)findViewById(R.id.forwardjlh17);
        forwardjmh8 = (ImageButton)findViewById(R.id.forwardjmh8);
        forwardjmh10 = (ImageButton)findViewById(R.id.forwardjmh10);
        forwardjmh13 = (ImageButton)findViewById(R.id.forwardjmh13);
        forwardjmh15 = (ImageButton)findViewById(R.id.forwardjmh15);
        forwardjmh17 = (ImageButton)findViewById(R.id.forwardjmh17);
        forwardjmeh8 = (ImageButton)findViewById(R.id.forwardjmeh8);
        forwardjmeh10 = (ImageButton)findViewById(R.id.forwardjmeh10);
        forwardjmeh13 = (ImageButton)findViewById(R.id.forwardjmeh13);
        forwardjmeh15 = (ImageButton)findViewById(R.id.forwardjmeh15);
        forwardjmeh17 = (ImageButton)findViewById(R.id.forwardjmeh17);
        forwardjjh8 = (ImageButton)findViewById(R.id.forwardjjh8);
        forwardjjh10 = (ImageButton)findViewById(R.id.forwardjjh10);
        forwardjjh13 = (ImageButton)findViewById(R.id.forwardjjh13);
        forwardjjh15 = (ImageButton)findViewById(R.id.forwardjjh15);
        forwardjjh17 = (ImageButton)findViewById(R.id.forwardjjh17);
        forwardjvh8 = (ImageButton)findViewById(R.id.forwardjvh8);
        forwardjvh10 = (ImageButton)findViewById(R.id.forwardjvh10);
        forwardjvh13 = (ImageButton)findViewById(R.id.forwardjvh13);
        forwardjvh15 = (ImageButton)findViewById(R.id.forwardjvh15);
        forwardjvh17 = (ImageButton)findViewById(R.id.forwardjvh17);
        forwardjsh8 = (ImageButton)findViewById(R.id.forwardjsh8);
        forwardjsh10 = (ImageButton)findViewById(R.id.forwardjsh10);
        forwardjsh13 = (ImageButton)findViewById(R.id.forwardjsh13);
        forwardjsh15 = (ImageButton)findViewById(R.id.forwardjsh15);
        forwardjsh17 = (ImageButton)findViewById(R.id.forwardjsh17);

        context = this;

        //Définition des animations
        clique = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition du listener des cliques sur les cases
        initAnim_clic();

        jlh8.setClickable(true);
        jlh8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.jlh8:
                        jlh8.startAnimation(clique);
                        removejlh8.setVisibility(View.VISIBLE);
                        forwardjlh8.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        

        //Définition des titres
        titleBar = getResources().getString(R.string.label_planning_prof);
        titleMenu = getResources().getString(R.string.label_menu);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_planning_prof);
        drawerListView = (ListView) findViewById(R.id.left_drawer_planning_prof);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_planning_prof);

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
                    intent = new Intent(PlanningProf.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(PlanningProf.this, PlanningSalle.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(PlanningProf.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(PlanningProf.this, Notes.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(PlanningProf.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(PlanningProf.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    //Définition du listener de l'animation du clic sur une case du tableau
    private void initAnim_clic() {
        clique.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}

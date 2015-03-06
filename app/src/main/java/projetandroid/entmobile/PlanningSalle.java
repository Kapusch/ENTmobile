package projetandroid.entmobile;

import android.app.ActionBar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlanningSalle extends ActionBarActivity{

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CharSequence titleBar;
    private CharSequence titleMenu;
    private Intent intent;
    private TextView jlh8, jlh10, jlh13, jlh15,jlh17, jmh8, jmh10, jmh13, jmh15, jmh17, jmeh8, jmeh10, jmeh13, jmeh15, jmeh17;
    private TextView jjh8, jjh10, jjh13, jjh15, jjh17, jvh8, jvh10, jvh13, jvh15, jvh17, jsh8, jsh10, jsh13, jsh15, jsh17;
    private ImageButton newjlh8, newjlh10, newjlh13, newjlh15, newjlh17, newjmh8, newjmh10, newjmh13, newjmh15, newjmh17, newjmeh8, newjmeh10, newjmeh13, newjmeh15, newjmeh17;
    private ImageButton newjjh8, newjjh10, newjjh13, newjjh15, newjjh17, newjvh8, newjvh10, newjvh13, newjvh15, newjvh17, newjsh8, newjsh10, newjsh13, newjsh15, newjsh17;
    private Context context;
    private Animation clique;

    private GridLayout tab_planning;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_salle);

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

        newjlh8 = (ImageButton)findViewById(R.id.newjlh8);
        newjlh10 = (ImageButton)findViewById(R.id.newjlh10);
        newjlh13 = (ImageButton)findViewById(R.id.newjlh13);
        newjlh15 = (ImageButton)findViewById(R.id.newjlh15);
        newjlh17 = (ImageButton)findViewById(R.id.newjlh17);
        newjmh8 = (ImageButton)findViewById(R.id.newjmh8);
        newjmh10 = (ImageButton)findViewById(R.id.newjmh10);
        newjmh13 = (ImageButton)findViewById(R.id.newjmh13);
        newjmh15 = (ImageButton)findViewById(R.id.newjmh15);
        newjmh17 = (ImageButton)findViewById(R.id.newjmh17);
        newjmeh8 = (ImageButton)findViewById(R.id.newjmeh8);
        newjmeh10 = (ImageButton)findViewById(R.id.newjmeh10);
        newjmeh13 = (ImageButton)findViewById(R.id.newjmeh13);
        newjmeh15 = (ImageButton)findViewById(R.id.newjmeh15);
        newjmeh17 = (ImageButton)findViewById(R.id.newjmeh17);
        newjjh8 = (ImageButton)findViewById(R.id.newjjh8);
        newjjh10 = (ImageButton)findViewById(R.id.newjjh10);
        newjjh13 = (ImageButton)findViewById(R.id.newjjh13);
        newjjh15 = (ImageButton)findViewById(R.id.newjjh15);
        newjjh17 = (ImageButton)findViewById(R.id.newjjh17);
        newjvh8 = (ImageButton)findViewById(R.id.newjvh8);
        newjvh10 = (ImageButton)findViewById(R.id.newjvh10);
        newjvh13 = (ImageButton)findViewById(R.id.newjvh13);
        newjvh15 = (ImageButton)findViewById(R.id.newjvh15);
        newjvh17 = (ImageButton)findViewById(R.id.newjvh17);
        newjsh8 = (ImageButton)findViewById(R.id.newjsh8);
        newjsh10 = (ImageButton)findViewById(R.id.newjsh10);
        newjsh13 = (ImageButton)findViewById(R.id.newjsh13);
        newjsh15 = (ImageButton)findViewById(R.id.newjsh15);
        newjsh17 = (ImageButton)findViewById(R.id.newjsh17);

        context = this;

        //Définition des éléments de la page
        tab_planning = (GridLayout)findViewById(R.id.cellule);

        //Définition des animations
        clique = AnimationUtils.loadAnimation(this, R.anim.fadein);

        //Définition du listener des cliques sur les cases
        initAnim_clique();

        initPlanning();

        //Définition des titres
        titleBar = getResources().getString(R.string.label_planning_salle);
        titleMenu = getResources().getString(R.string.label_menu);

        // Récupérer les items du menu et la vue du menu
        drawerListViewItems = getResources().getStringArray(R.array.menu_items_planning_salle);
        drawerListView = (ListView) findViewById(R.id.left_drawer_planning_salle);

        // Initialisation de l'adapter
        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_listview_item, drawerListViewItems));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_planning_salle);

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


    //Initialisation des éléments du planning
    private void initPlanning() {
        int max = tab_planning.getChildCount();
        int i = 0, j, endRow = 8, rowCount = 0;
        ((TextView) tab_planning.getChildAt(0)).setText("L1 Algo");
        ((TextView) tab_planning.getChildAt(2)).setText("L1 Algo");
        ((TextView) tab_planning.getChildAt(6)).setText("L1 Algo");
        ((TextView) tab_planning.getChildAt(12)).setText("L1 Algo");
        ((TextView) tab_planning.getChildAt(16)).setText("L1 Algo");
        ((TextView) tab_planning.getChildAt(18)).setText("L1 Algo");
        ((TextView) tab_planning.getChildAt(50)).setText("L1 Algo");
//        Toast.makeText(getApplicationContext(), String.valueOf(max), Toast.LENGTH_SHORT).show();
        while (rowCount < 6) {
            for (j = i; j <= endRow; j += 2) {
                final TextView cellule_temp = (TextView) tab_planning.getChildAt(j);
                if ((cellule_temp.getText().equals(getResources().getString(R.string.vide)))) {
                    cellule_temp.setClickable(true);
                    final ImageButton new_button_temp = (ImageButton) tab_planning.getChildAt(j + 1);
                    new_button_temp.setVisibility(View.VISIBLE);
                    new_button_temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new_button_temp.startAnimation(clique);
                            }
                    });
                }
            }
            i = j;
            endRow = i + 8;
            rowCount++;
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
                    intent = new Intent(PlanningSalle.this, Accueil.class);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(PlanningSalle.this, PlanningProf.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(PlanningSalle.this, Reservation.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(PlanningSalle.this, Notes.class);
                    startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(PlanningSalle.this, Profil.class);
                    startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(PlanningSalle.this, Connexion.class);
                    startActivity(intent);
                    /* TODO */
                    /* Définir la déconnexion de l'utilisateur à l'application */
                    Toast.makeText(getApplicationContext(), R.string.boutonCoDeconnexionToast, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    private void initAnim_clique() {
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

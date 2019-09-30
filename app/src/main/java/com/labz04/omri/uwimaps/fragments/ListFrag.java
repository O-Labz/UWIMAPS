package com.labz04.omri.uwimaps.fragments;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.labz04.omri.uwimaps.R;
import com.labz04.omri.uwimaps.adapter.CustomAdapter;
import com.labz04.omri.uwimaps.adapter.DataAdapter;
import com.labz04.omri.uwimaps.comparator.PointWithDistance;
import com.labz04.omri.uwimaps.database.LocationDatabase;
import com.labz04.omri.uwimaps.fragments.SearchFragment;
import com.labz04.omri.uwimaps.gps.GPS_MODULE;
import com.labz04.omri.uwimaps.shortestpath.Dijkstra;
import com.labz04.omri.uwimaps.shortestpath.Graph;
import com.labz04.omri.uwimaps.uwimaps.MainActivity;
import com.labz04.omri.uwimaps.uwimaps.Student;
import com.labz04.omri.uwimaps.uwimaps.StudentRepo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omri on 4/2/16.
 */

public class ListFrag extends ListFragment
{
    private String text;
    private CustomAdapter customAdapter;
    private ListView listView;
    private SearchView searchview;
    private Cursor cursor;
    private StudentRepo studentRepo ;
    SearchManager manager;
    private final static String TAG = MainActivity.class.getName().toString();
    private static int Screen_Delay = 3000;

    HashMap<String,List<String>> faculty_category;
    List<PointWithDistance> helperList= new ArrayList<>();

    List<String> department_list;
    DataAdapter adapter;
    ExpandableListView Exp_list;
    double DIstance,Distance;

    Dijkstra find = new Dijkstra();
    Graph g = new Graph(find.GRAPH);
    MapFragment yes = new MapFragment();



    GPS_MODULE gps;
    public String Min;
    String dne;

    public String START = Min;
    public String END = dne;

    LocationDatabase myDB;


    public ListFrag()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);

        studentRepo = new StudentRepo(getActivity());
        cursor = studentRepo.getStudentList();
        customAdapter = new CustomAdapter(getActivity(),  cursor, 0);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View row = inflater.inflate(R.layout.list_layout, container, false);


//        setHasOptionsMenu(true);
//
//        studentRepo = new StudentRepo(getActivity());
//        cursor = studentRepo.getStudentList();
//        customAdapter = new CustomAdapter(getActivity(),  cursor, 0);
        listView = (ListView) row.findViewById(android.R.id.list);
        listView.setAdapter(customAdapter);
        listView.requestFocusFromTouch();

        if(cursor==null)
        {
            insertDummy();
        }

        load_data.start();

        return row;
    }

    private double distance(LatLng current, LatLng last) {
        if (last == null)
            return 0;
        Location cL = new Location("");
        cL.setLatitude(current.latitude);
        cL.setLongitude(current.longitude);

        Location lL = new Location("");
        lL.setLatitude(last.latitude);
        lL.setLongitude(last.longitude);

        DIstance = (double) lL.distanceTo(cL);

        return DIstance;
    }


    // This class finds the closest node in the map to your current location.
    class DistanceComparator implements Comparator<PointWithDistance> {
        @Override
        public int compare(PointWithDistance lhs, PointWithDistance rhs) {
//                    double dis1 = lhs.getDistance();
//                    double dis2 = rhs.getDistance();
//                    if ( dis1 > dis2 )
//                    {
//                        return 1;
//                    }
//                    else if ( dis1 < dis2 )
//                    {
//                        return -1;
//                    }
//                    return 0;
            return (lhs.getDistance() < rhs.getDistance() ? -1 : (lhs.getDistance() == rhs.getDistance() ? 0 : 1));
        }

    }

    public void closestNode() {
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        LocationDatabase myDB = new LocationDatabase(getActivity());
        Cursor CSR = myDB.get_Data(myDB);
        CSR.moveToFirst();
        LatLng current = new LatLng(latitude, longitude);
        do {
            CSR.getString(0);
            CSR.getDouble(1);
            CSR.getDouble(2);
            double Dlat = CSR.getDouble(1);
            double Dlong = CSR.getDouble(2);
            Location cLoc = new Location("");
            cLoc.setLatitude(current.latitude);
            cLoc.setLongitude(current.longitude);

            Location lLoc = new Location("");
            lLoc.setLatitude(Dlat);
            lLoc.setLongitude(Dlong);

            Distance = (double) lLoc.distanceTo(cLoc);
            PointWithDistance helper = new PointWithDistance(CSR.getString(0), Dlat, Dlong, Distance);
            helperList.add(helper);
            // Log.i("values", helper.getname()); //shows list content in log


        }
        while (CSR.moveToNext());
        Collections.sort(helperList, new DistanceComparator());
        // Since list is sorted above according to shortest distance based on your current location
        // Min is the item that is at the top of the list which is also the closest place to where you are
        Min = helperList.get(0).getname();
//                // Loop through elements of the list to see what is being added into the list.
//                for (int i = 0; i < helperList.size(); i++) {
//                    Object value = helperList.get(i).getDistance();
//                    System.out.println("Element: " + value);
//                }

        //Toast.makeText(getActivity(), "name:" + Min, Toast.LENGTH_SHORT).show();


    }

    //Show All Data.....this function is used to see the contents of the database
    public void Showmessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            searchview = (SearchView) menu.findItem(R.id.search).getActionView();
            searchview.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
            searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String s)
                {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor = studentRepo.getStudentListByKeyword(s);
                    if (cursor == null) {
                        Toast.makeText(getActivity(), "No records found!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), cursor.getCount() + " records found!", Toast.LENGTH_LONG).show();
                    }
                    customAdapter.swapCursor(cursor);
                    searchview.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor = studentRepo.getStudentListByKeyword(s);
                    if (cursor != null) {
                        customAdapter.notifyDataSetChanged();
                        listView.setAdapter(customAdapter);
                        customAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });
        }

    }


    Thread load_data = new Thread(new Runnable() {
        public void run()
        {
            // Insert some method call here.
            //Get Item when text view clicked
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                private double distance(LatLng current, LatLng last) {
//                    if (last == null)
//                        return 0;
//                    Location cL = new Location("");
//                    cL.setLatitude(current.latitude);
//                    cL.setLongitude(current.longitude);
//
//                    Location lL = new Location("");
//                    lL.setLatitude(last.latitude);
//                    lL.setLongitude(last.longitude);
//
//                    DIstance = (double) lL.distanceTo(cL);
//
//                    return DIstance;
//                }
//
//
//                // This class finds the closest node in the map to your current location.
//                class DistanceComparator implements Comparator<PointWithDistance> {
//                    @Override
//                    public int compare(PointWithDistance lhs, PointWithDistance rhs) {
////                    double dis1 = lhs.getDistance();
////                    double dis2 = rhs.getDistance();
////                    if ( dis1 > dis2 )
////                    {
////                        return 1;
////                    }
////                    else if ( dis1 < dis2 )
////                    {
////                        return -1;
////                    }
////                    return 0;
//                        return (lhs.getDistance() < rhs.getDistance() ? -1 : (lhs.getDistance() == rhs.getDistance() ? 0 : 1));
//                    }
//
//                }
//
//                public void closestNode() {
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//                    LocationDatabase myDB = new LocationDatabase(getActivity());
//                    Cursor CSR = myDB.get_Data(myDB);
//                    CSR.moveToFirst();
//                    LatLng current = new LatLng(latitude, longitude);
//                    do {
//                        CSR.getString(0);
//                        CSR.getDouble(1);
//                        CSR.getDouble(2);
//                        double Dlat = CSR.getDouble(1);
//                        double Dlong = CSR.getDouble(2);
//                        Location cLoc = new Location("");
//                        cLoc.setLatitude(current.latitude);
//                        cLoc.setLongitude(current.longitude);
//
//                        Location lLoc = new Location("");
//                        lLoc.setLatitude(Dlat);
//                        lLoc.setLongitude(Dlong);
//
//                        Distance = (double) lLoc.distanceTo(cLoc);
//                        PointWithDistance helper = new PointWithDistance(CSR.getString(0), Dlat, Dlong, Distance);
//                        helperList.add(helper);
//                        // Log.i("values", helper.getname()); //shows list content in log
//
//
//                    }
//                    while (CSR.moveToNext());
//                    Collections.sort(helperList, new DistanceComparator());
//                    // Since list is sorted above according to shortest distance based on your current location
//                    // Min is the item that is at the top of the list which is also the closest place to where you are
//                    Min = helperList.get(0).getname();
////                // Loop through elements of the list to see what is being added into the list.
////                for (int i = 0; i < helperList.size(); i++) {
////                    Object value = helperList.get(i).getDistance();
////                    System.out.println("Element: " + value);
////                }
//
//                    //Toast.makeText(getActivity(), "name:" + Min, Toast.LENGTH_SHORT).show();
//
//
//                }
//
//                //Show All Data.....this function is used to see the contents of the database
//                public void Showmessage(String title, String message) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(true);
//                    builder.setTitle(title);
//                    builder.setMessage(message);
//                    builder.show();
//                }

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                    String Item_name = listView.getItemAtPosition(position).toString();
                    TextView textView = (TextView) view.findViewById(R.id.txtName);
                    text = textView.getText().toString();
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

                    String child_name = text;
                    dne = child_name;
                    gps = new GPS_MODULE(getActivity());
                    closestNode();
                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        LatLng current = new LatLng(latitude, longitude);

                        LocationDatabase myDB = new LocationDatabase(getActivity());
                        Cursor CSR = myDB.get_Data(myDB);
                        CSR.moveToFirst();
                        boolean mapping_stat = false;
                        // Loop through elements of the list to see what is being added into the list.

                        do {
                            //If the user's name equals to the string in its respective column
                            if (Min.equals(CSR.getString(0))) {
                                mapping_stat = true;
                                FragmentManager fragmentManager = getFragmentManager();
                                //yes.one = CSR.getDouble(1);
                                //yes.two = CSR.getDouble(2);
                                Double Dlat = CSR.getDouble(1);
                                Double Dlong = CSR.getDouble(2);
                                LatLng destin = new LatLng(Dlat, Dlong);
                                g.dijkstra(Min);
                                g.printPath(child_name);
                                for (int hold = 0; hold < g.patth.size(); hold++)
                                {
                                    String value = g.patth.get(hold).toString();
                                    yes.come.add(value);
                                    System.out.println("Element: " + value);
                                }
                                yes.getActivity();
                                distance(current, destin);
                                fragmentManager.beginTransaction().replace(R.id.content_only, yes).commit();
                            }
                        }
                        //Loops through just incase there is more than one information in the database
                        while (CSR.moveToNext());
                        //If search is successful
                        if (mapping_stat) {
                            //Toast.makeText(getActivity(), String.valueOf(g.patth.size()), Toast.LENGTH_LONG).show();
                            Showmessage("Take This Path", g.buffer.toString());
                            Toast.makeText(getActivity(), "Ok lets go to: " + child_name + "\nYour Location is -\nLat: " + latitude + "\nLong: " + longitude + "\nDistance: " + DIstance, Toast.LENGTH_LONG).show();
//                        CSR.close();
                        } else {
                            Toast.makeText(getActivity(), "Not Yet in Database", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });
        }
    });


    private void insertDummy(){
        Student student= new Student();
        studentRepo = new StudentRepo(getActivity()); student.name="Student Administration Services";student.latitude = 18.005979	; student.longitude = -76.747481; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Mona Information Technology Services" ;student.latitude = 18.003268; student.longitude = -76.745261; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Placement & Careers" ;student.latitude = 18.004786; student.longitude = -76.746695; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="U.W.I Chapel" ;student.latitude = 18.003614; student.longitude = -76.748096; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Book Shop" ;student.latitude = 18.004780; student.longitude = -76.747066; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="National Commercial Bank" ;student.latitude = 18.004881; student.longitude = -76.747011; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Bank of Nova Scotia" ;student.latitude = 18.005509; student.longitude = -76.744914; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Jamaica National" ;student.latitude = 18.0061076; student.longitude = -76.7448295; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="U.W.I Credit Union" ;student.latitude = 18.0015973; student.longitude = -76.7457329; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Students Union" ;student.latitude = 18.000734; student.longitude = -76.743382; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Bursary" ;student.latitude = 18.005979	; student.longitude = -76.747481; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lodgings " ;student.latitude = 18.002791; student.longitude = -76.746298; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="News Talk" ;student.latitude = 18.004786; student.longitude = -76.746695; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Phillip Sherlock" ;student.latitude = 18.003536; student.longitude = -76.746307; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="CARIMAC" ;student.latitude = 18.003227; student.longitude = -76.747022; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Office of Student Services and Development" ;student.latitude = 18.002842; student.longitude = -76.746820; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Commuting Students Lounge";student.latitude = 18.0051758; student.longitude = -76.745592; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Assembly Hall";student.latitude = 18.005395; student.longitude = -76.747325; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Main Office" ;student.latitude = 18.0044993; student.longitude = -76.7491293; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 1" ;student.latitude = 18.004303; student.longitude = -76.749864; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 2" ;student.latitude = 18.004303; student.longitude = -76.749864; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 3" ;student.latitude = 18.004453; student.longitude = -76.749619; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 4" ;student.latitude = 18.004529; student.longitude = -76.750232; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 5" ;student.latitude = 18.004282; student.longitude = -76.750224; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 6" ;student.latitude = 18.004577; student.longitude = -76.750051; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Lecture Theater 7" ;student.latitude = 18.004645; student.longitude = -76.749950; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chemistry Physics Lecture Theater" 	;student.latitude = 18.004392; student.longitude = -76.749100; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Computing Main Office" ;student.latitude = 18.006016; student.longitude = -76.749597; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Computing Lecture Room" ;student.latitude = 18.005901; student.longitude = -76.749803; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="CR1" ;student.latitude = 18.005157	; student.longitude = -76.750003; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="CR2" ;student.latitude = 18.0051564; student.longitude = -76.750038; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lab1" ;student.latitude = 18.0052025; student.longitude = -76.7499753; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lab2" ;student.latitude = 18.005901; student.longitude = -76.749803; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Software Engineering Lab" ;student.latitude = 18.0064435; student.longitude = -76.7485883; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Room 1" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Room 2" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Room 3" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Room 4" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Room 5" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Room 6" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Museum" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Marine Geology Unit" ;student.latitude = 18.006016; student.longitude = -76.749323; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lab 1" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lab 2" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lab 3" ;student.latitude = 18.006131; student.longitude = -76.749020; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Main Office" ;student.latitude = 18.006182; student.longitude = -76.750341; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater 1" ;student.latitude = 18.006182; student.longitude = -76.750342; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater 2" ;student.latitude = 18.006182; student.longitude = -76.750343; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater 3" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater 4" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater 5" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Aquatic Sciences Lab" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block A" ;student.latitude = 18.006182; student.longitude = -76.750348; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block B" ;student.latitude = 18.006182; student.longitude = -76.750346; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block C" ;student.latitude = 18.006182; student.longitude = -76.750344; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block D" ;student.latitude = 18.006182; student.longitude = -76.750345; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block E" ;student.latitude = 18.006182; student.longitude = -76.750345; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Physics Main Office" ;student.latitude = 18.004836	; student.longitude = -76.748823; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Phys A" ;student.latitude = 18.004928; student.longitude = -76.749047; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Phys B" ;student.latitude = 18.0049536; student.longitude = -76.7490518; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Phys C" ;student.latitude = 18.0049459; student.longitude = -76.7490336; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Phys D" ;student.latitude = 18.004500; student.longitude = -76.749037; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Electronics Lab" ;student.latitude = 18.004620; student.longitude = -76.748906; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Virtual Lab" ;student.latitude = 18.004586; student.longitude = -76.749019; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Earthquake Unit" ;student.latitude = 18.004586; student.longitude = -76.749019; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Electronics Unit" ;student.latitude = 18.004586; student.longitude = -76.749019; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Engineering Main Office" ;student.latitude = 18.004615; student.longitude = -76.750417; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Engineering Lecture Theater 1" ;student.latitude = 18.0051555; student.longitude = -76.7497084; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Engineering Lecture Theater 2" ;student.latitude = 18.0051632; student.longitude = -76.7497196; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Engineering Lecture Theater 3" ;student.latitude = 18.0051548; student.longitude = -76.749703; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Engineering Lecture Theater 4" ;student.latitude = 18.0050984; student.longitude = -76.7496972; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Engineering Lecture Theater 5" ;student.latitude = 18.0051141; student.longitude = -76.7496359; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Electronics Engineering Lab" ;student.latitude = 18.0051221; student.longitude = -76.7495705; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Humanities Gazebos"	;student.latitude = 18.0048848	; student.longitude = -76.7462619; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Nursing Gazebos" ;student.latitude = 18.003946; student.longitude = -76.744698; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Science and Tech Gazebos" ;student.latitude = 18.005288; student.longitude = -76.748512; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Law Gazebos" ;student.latitude = 18.008171; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Mona School of Business Gazebos" ;student.latitude = 18.008303; student.longitude = -76.748074; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Commuting Lounge Gazebos";student.latitude = 18.0051758; student.longitude = -76.745592; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Chancellor Hall Office" ;student.latitude = 18.005720; student.longitude = -76.744179; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Aye" ;student.latitude = 18.005982; student.longitude = -76.744221; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Runci" ;student.latitude = 18.006530; student.longitude = -76.744502; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Che'" ;student.latitude = 18.006026; student.longitude = -76.743852; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Dynamite" ;student.latitude = 18.006647; student.longitude = -76.743992; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block X" ;student.latitude = 18.005619; student.longitude = -76.744567; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="ABC Hall Office";student.latitude = 18.012959; student.longitude = -76.743555; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="ABC Block A";student.latitude = 18.013131; student.longitude = -76.743253; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="ABC Block B";student.latitude = 18.013131; student.longitude = -76.743253; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="ABC Block C";student.latitude = 18.013131; student.longitude = -76.743253; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Taylor Hall Office";student.latitude = 18.007293; student.longitude = -76.744232; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Attica";student.latitude = 18.008012; student.longitude = -76.744153; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Butchers";student.latitude = 18.007970; student.longitude = -76.744796; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Roosters";student.latitude = 18.007495; student.longitude = -76.744007; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Stallion";student.latitude = 18.007360; student.longitude = -76.744486; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block G";student.latitude = 18.008415; student.longitude = -76.744475; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Tower Hall Office";student.latitude = 18.007665; student.longitude = -76.742904; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Tower 1";student.latitude = 18.008046; student.longitude = -76.743466; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Tower 2";student.latitude = 18.007802; student.longitude = -76.743489; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Tower 3";student.latitude = 18.007665; student.longitude = -76.742904; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Tower 4";student.latitude = 18.007243; student.longitude = -76.743589; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Tower 5";student.latitude = 18.006986; student.longitude = -76.743609; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Mary Seacole Hall Office";student.latitude = 18.004750; student.longitude = -76.745292; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Jade Block";student.latitude = 18.004024; student.longitude = -76.745460; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Great Block";student.latitude = 18.004055; student.longitude = -76.745089; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Hype Block";student.latitude = 18.004349; student.longitude = -76.745482; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Crowd Block";student.latitude = 18.004544; student.longitude = -76.745215; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Seacole Block Dynamite";student.latitude = 18.004558; student.longitude = -76.745024; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Irvin Hall Office";student.latitude = 18.006083; student.longitude = -76.742349; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Alpha";student.latitude = 18.006198; student.longitude = -76.742466; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Bears";student.latitude = 18.006432; student.longitude = -76.742841; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Block Chalise";student.latitude = 18.006746; student.longitude = -76.742501; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Freshers Block";student.latitude = 18.007306; student.longitude = -76.742814; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Rex Hall Office";student.latitude = 18.002636; student.longitude = -76.741620; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 1";student.latitude = 18.001685; student.longitude = -76.741456; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 2";student.latitude = 18.001798; student.longitude = -76.741715; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 3";student.latitude = 18.002032; student.longitude = -76.742028; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 4";student.latitude = 18.002473; student.longitude = -76.742419; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 5";student.latitude = 18.002892; student.longitude = -76.742475; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 6";student.latitude = 18.003125; student.longitude = -76.742189; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 7";student.latitude = 18.003683; student.longitude = -76.742070; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 8";student.latitude = 18.003161; student.longitude = -76.741626; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster 9";student.latitude = 18.003629; student.longitude = -76.741418; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Preston Hall Office";student.latitude = 18.000490; student.longitude = -76.742613; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Amsterdam";student.latitude = 18.001388; student.longitude = -76.742028; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Belqique";student.latitude = 18.001317; student.longitude = -76.742538; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Burgplatz";student.latitude = 18.001424; student.longitude = -76.742843; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Einheit";student.latitude = 17.999858; student.longitude = -76.741872; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Italia";student.latitude = 18.000169; student.longitude = -76.741829; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Los Matadores";student.latitude = 18.000516; student.longitude = -76.742017; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Sherwood Manor";student.latitude = 17.999944; student.longitude = -76.742940; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Regensen";student.latitude = 17.999893; student.longitude = -76.742189; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster Olympia";student.latitude = 18.000970; student.longitude = -76.742350; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Cluster La Maison";student.latitude = 18.001742; student.longitude = -76.742551; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="138 Office";student.latitude = 18.002074; student.longitude = -76.744160; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="T 1";student.latitude = 18.002247; student.longitude = -76.744059; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="T 2";student.latitude = 18.002166; student.longitude = -76.743909; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="T 3";student.latitude = 18.002166; student.longitude = -76.743909; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="New Post Grad";student.latitude = 18.000566; student.longitude = -76.745873; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Old PostGrad";student.latitude = 18.001680; student.longitude = -76.743406; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Humanities Faculty Main Office" ;student.latitude = 18.0052098; student.longitude = -76.7461714; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="N 1" ;student.latitude = 18.0051723; student.longitude = -76.746152; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="N 2" ;student.latitude = 18.0051884; student.longitude = -76.746152; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="N 3" ;student.latitude = 18.0051908; student.longitude = -76.7461325; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="N 4" ;student.latitude = 18.0052132; student.longitude = -76.7461167; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="NELT" ;student.latitude = 18.0057788; student.longitude = -76.7466837; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Caribbean Studies" ;student.latitude = 18.0052622; student.longitude = -76.7461434; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="English Lit" ;student.latitude = 18.0052255; student.longitude = -76.7461625; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="School of Education" ;student.latitude = 18.005354; student.longitude = -76.745676; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Modern Language and literature" ;student.latitude = 18.004788; student.longitude = -76.7467708; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Library and Information Studies" ;student.latitude = 18.0047863	; student.longitude = -76.746868; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="School of Education Lecture Theater" ;student.latitude = 18.005354; student.longitude = -76.745676; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Law Faculty Main Office" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lecture Room 1A" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lecture Room 2B" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lecture Room 1" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lecture Room 2" ;student.latitude = 18.008171; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Lecture Room 3" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Seminar Room 1" ;student.latitude = 18.008244; student.longitude = -76.748480; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Seminar Room 2" ;student.latitude = 18.008171; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Science Lecture Theater" ;student.latitude = 18.005187; student.longitude = -76.749936; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Pre Clinical Theater1" ;student.latitude = 18.005139; student.longitude = -76.749792; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Inter-faculty Lecture Theater" ;student.latitude = 18.005653; student.longitude = -76.748783; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Biology Lecture Theater" ;student.latitude = 18.006084; student.longitude = -76.750487; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Social Science Lecture Theater" ;student.latitude = 18.006695; student.longitude = -76.747406; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="GLT 1" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="GLT 2" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="GLT 3" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="SR  1 - 23" ;student.latitude = 18.0071234; student.longitude = -76.7471699; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Psychology Unit" ;student.latitude = 18.007913; student.longitude = -76.747629; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="New Arts Block" ;student.latitude = 18.0049568; student.longitude = -76.7464424; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Main Office" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater1" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater2" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater3" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater4" ;student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Life Science Lecture Theater5";student.latitude = 18.006182; student.longitude = -76.750340; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Math Main Office" ;student.latitude = 18.004714; student.longitude = -76.749561; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Math Lecture theater 1" ;student.latitude = 18.004714; student.longitude = -76.749561; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Math Lecture theater 2" ;student.latitude = 18.004714; student.longitude = -76.749561; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Math Lecture theater 3"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Math Lab"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Postgrad Room 1"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Postgrad Room 2"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Postgrad Room 3"; student.longitude = 18.004748; student.longitude = -76.749460; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Main Library" ;student.latitude = 18.006332; student.longitude = -76.745874; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Science Library" ;student.latitude = 18.005309; student.longitude = -76.749467; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Social Science Library" ;student.latitude = 18.006381; student.longitude = -76.748001; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Med Library" ;student.latitude = 18.010876; student.longitude = -76.745790; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Law Library" ;student.latitude = 18.008171	; student.longitude = -76.748520; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Old Library" ;student.latitude = 18.000991; student.longitude = -76.744840; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Med Faculty Main Office" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Child and Adolescents" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Community Health Psychiatry" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Medicine" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Micro Biology" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Surgery" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="School of Nursing" ;student.latitude = 18.003890; student.longitude = -76.744349; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Radiology" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Anaesthesia" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Intensive Care" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Obstetrics Gynaecology" ;student.latitude = 18.009653; student.longitude = -76.746733; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Kentucky Fried Chicken" ;student.latitude = 18.006140; student.longitude = -76.744805; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="The Spot" ;student.latitude = 18.000793; student.longitude = -76.743276; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Yao" ;student.latitude = 18.000793; student.longitude = -76.743276; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Juici Patties" ;student.latitude = 18.005114; student.longitude = -76.748565; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Pages Cafe'" ;student.latitude = 18.006234; student.longitude = -76.744835; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="BeeHive" ;student.latitude = 18.004488; student.longitude = -76.746364; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Dukunoo Deli" ;student.latitude = 18.007227; student.longitude = -76.747139; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Bucks" ;student.latitude = 18.007630; student.longitude = -76.748592; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Faculty Office" ;student.latitude = 18.0071244; student.longitude = -76.7471703; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Hotel and Tourism" 	;student.latitude = 18.007125; student.longitude = -76.7471669; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Economics" ;student.latitude = 18.006531; student.longitude = -76.746878; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Government" ;student.latitude = 18.006531; student.longitude = -76.746878; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Mona School of Business North" ;student.latitude = 18.007952; student.longitude = -76.747375; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Mona School of Business South" ;student.latitude = 18.006381; student.longitude = -76.748001; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Sociology and Social Work" 	;student.latitude = 18.0071231; student.longitude = -76.7471676; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Psychology Unit" ;student.latitude = 18.0071241; student.longitude = -76.7471698; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Institute for Gender and Development" 	;student.latitude = 18.0071241; student.longitude = -76.7471698; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Main Library Stall" ;student.latitude = 18.005484; student.longitude = -76.745527; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Humanities Stall" 	;student.latitude = 18.0050561; student.longitude = -76.7459129; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Social Science Stall" ;student.latitude = 18.0065191; student.longitude = -76.7469276; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Science and Tech Stall 1" ;student.latitude = 18.0049263; student.longitude = -76.74912; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="Science and Tech Stall 2" ;student.latitude = 18.0049442; student.longitude = -76.74912; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="UWI Health Center";student.latitude = 18.004315; student.longitude = -76.743712; studentRepo.insert(student);
        studentRepo = new StudentRepo(getActivity()); student.name="University Hospital";student.latitude = 18.011353; student.longitude = -76.744420; studentRepo.insert(student);

    }

}

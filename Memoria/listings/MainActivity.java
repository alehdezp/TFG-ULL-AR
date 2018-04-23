
public class MainActivity extends AppCompatActivity {
    /*Different elements for Navigation drawer*/
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    /*We use this attribute to know which module has been selected*/
    private int functionality;

    //...

    /*Diplaying fragment view for selected Nav drawer list item*/
    private void displayView(int position) {
        // Updating the main content by replacing fragments
        Fragment fragment = null;
        Bundle bundle=null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new FindFragment();
                bundle = new Bundle();
                functionality=1;
                bundle.putInt("functionality", functionality);
                fragment.setArguments(bundle);
                break;
            //...
        }

        if (fragment != null) {
            //Commit the transaction of the new fragment.
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // Update selected item and title, then close the drawer.
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // Error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
//...
}


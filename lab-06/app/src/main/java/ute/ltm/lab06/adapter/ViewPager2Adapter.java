package ute.ltm.lab06.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ute.ltm.lab06.fragment.NewOrderFragment;
import ute.ltm.lab06.fragment.PickupFragment;
import ute.ltm.lab06.fragment.DeliveryFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new NewOrderFragment();
            case 1: return new PickupFragment();
            case 2: return new DeliveryFragment();
            default: return new NewOrderFragment();
        }
    }

    @Override
    public int getItemCount() { return 3; }
}
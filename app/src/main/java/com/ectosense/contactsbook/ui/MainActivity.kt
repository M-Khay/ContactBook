package com.ectosense.contactsbook.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ContactListFragment.ChangeFragmentListener,
    ActionBarCallBack {

    private val contactViewModel by viewModel<ContactViewModel>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        if (savedInstanceState == null) {
            val fm = supportFragmentManager
            fm.beginTransaction()
                .add(R.id.container, ContactListFragment())
                .commit()
        }
        contactViewModel.selectedContact.observe(this, selectedContactObserver)
    }

    override fun changeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddOrEditContactFragment())
            .addToBackStack(null)
            .commit()
    }

    private val selectedContactObserver = Observer<Person> {
        it?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactDetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun showActionBarWithIcon(title: String?, showBackButton: Boolean) {
        toolbar.title = title
        if (showBackButton) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24px)
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_app_icon)
        }
    }
}

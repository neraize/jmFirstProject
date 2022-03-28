package com.neraize.jmfirstproject

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.neraize.jmfirstproject.adapers.MyAdminListAdapter
import com.neraize.jmfirstproject.databinding.ActivityAdministratorBinding

class AdministratorActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding:ActivityAdministratorBinding

    companion object{
        lateinit var txtName: TextView
        lateinit var spnPossibility:String
    }

    lateinit var mAdapter: MyAdminListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_administrator)

        // spinner 연결
        ArrayAdapter.createFromResource(
            mContextt,
            R.array.my_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }
        binding.spinner.onItemSelectedListener = this

        SetupEvents()
        SetValues()
    }

    override fun SetupEvents() {

    }

    override fun SetValues() {

        txtName = binding.txtName

        txtProfile.visibility = View.GONE
        txtProfileOut.visibility = View.VISIBLE

        // 관리자 화면 나가기
        txtProfileOut.setOnClickListener {
            finish()
        }


        mAdapter = MyAdminListAdapter(mContextt, R.layout.my_admin_list_item, SplashActivity.mCountryList)
        binding.myAdministratorListView.adapter = mAdapter

        // 어댑터 새로고침
        //mAdapter.notifyDataSetChanged()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0-> spnPossibility ="여행가능"
            1-> spnPossibility ="국내격리"
            2-> spnPossibility ="국내/국외격리"
            else-> spnPossibility ="국내/입국금지"
        }

        //Toast.makeText(mContextt, spnPossibility, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}
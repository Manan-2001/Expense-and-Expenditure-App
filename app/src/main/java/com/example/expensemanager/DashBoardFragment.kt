package com.example.expensemanager

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.expensemanager.Model.Data
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date
import java.text.DateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashBoardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var fab_main_btn: FloatingActionButton
    private lateinit var fab_income_btn: FloatingActionButton
    private lateinit var fab_expense_btn: FloatingActionButton

    private lateinit var fab_income_txt:TextView
    private lateinit var fab_expense_txt:TextView

    //boolean

    private var isOpen =false

    //animation

    private lateinit var fade_open:Animation
    private lateinit var fade_close:Animation


    //FireBase Database

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mIncomeDatabase:DatabaseReference
    private lateinit var mExpenseDatabase:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myview:View=inflater.inflate(R.layout.fragment_dash_board, container, false)

        mAuth=FirebaseAuth.getInstance()
        val mUser: FirebaseUser? =mAuth.currentUser
        val uid: String? = mUser?.uid
        if (uid != null) {
            mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid)
        }
        if (uid != null) {
            mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid)
        }



        //connnect floating button
        fab_main_btn=myview.findViewById(R.id.fb_main_plus_btn)
        fab_income_btn=myview.findViewById(R.id.income_Ft_btn)
        fab_expense_btn=myview.findViewById(R.id.expense_ft_button)

        //connect floating text
        fab_income_txt=myview.findViewById(R.id.income_ft_text)
        fab_expense_txt=myview.findViewById(R.id.expense_ft_text)

        fade_open=AnimationUtils.loadAnimation(activity,R.anim.fade_open)
        fade_close=AnimationUtils.loadAnimation(activity,R.anim.fade_close)

        fab_main_btn.setOnClickListener{
            addData()
            if (isOpen){
                fab_income_btn.startAnimation(fade_close)
                fab_expense_btn.startAnimation(fade_close)
                fab_income_btn.isEnabled = false
                fab_expense_btn.isEnabled = false
                fab_income_txt.startAnimation(fade_close)
                fab_expense_txt.startAnimation(fade_close)
                fab_income_txt.isEnabled = false
                fab_expense_txt.isEnabled = false
                isOpen=false
            }else{
                fab_income_btn.startAnimation(fade_open)
                fab_expense_btn.startAnimation(fade_open)
                fab_income_btn.isEnabled = true
                fab_expense_btn.isEnabled = true
                fab_income_txt.startAnimation(fade_open)
                fab_expense_txt.startAnimation(fade_open)
                fab_income_txt.isEnabled = true
                fab_expense_txt.isEnabled = true
                isOpen=false
            }
        }

        return myview
    }


    private fun addData(){
    //Fab Button
        fab_income_btn.setOnClickListener{
        incomeDataInsert()
        }
        fab_expense_btn.setOnClickListener{

        }
    }

    fun incomeDataInsert(){
        val mydialog:AlertDialog.Builder=AlertDialog.Builder(activity)
        val inflater:LayoutInflater=LayoutInflater.from(activity)

        val myview:View=inflater.inflate(R.layout.custome_layout_for_insertdata,null)
        mydialog.setView(myview)

        val dialog:AlertDialog=mydialog.create()


        val edtAmount:EditText=myview.findViewById(R.id.ammount_edt)
        val edtType:EditText=myview.findViewById(R.id.type_edt)
        val edtNote:EditText=myview.findViewById(R.id.note_edt)
        val btnSave:Button=myview.findViewById(R.id.btnSave)
        val btnCancel:Button=myview.findViewById(R.id.btnCancle)


        btnSave.setOnClickListener{
            var type:String=edtType.text.toString()
            var ammount:String=edtAmount.text.toString()
            var note:String=edtNote.text.toString()

            if (type.isEmpty()){
                edtType.error="Mandatory to Fill"
            }
            if (ammount.isEmpty()){
                edtAmount.error="Mandatory to Fill"
            }
            var ourammountint:Int=ammount.toInt()

            if (note.isEmpty()) {
                edtNote.error="Mandatory to Fill"

            }
            var id =mIncomeDatabase.push().key!!
            val mDate: String? = DateFormat.getDateInstance().format(Date())
            val idata=Data(ourammountint, type, note, id, mDate)
             mIncomeDatabase.child(id).setValue(idata)
             Toast.makeText(activity,"DATA ADDED",Toast.LENGTH_SHORT).show()
                dialog.dismiss()



        }
        btnCancel.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }
}
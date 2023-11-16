package com.example.expensemanager.Model

class Data {
    private var amount: Int = 0
    private var type: String? = null
    private var note: String? = null
    private var id: String? = null
    private var date: String? = null

    constructor(amount: Int, type: String?, note: String?, id: String?, date: String?) {
        this.amount = amount
        this.type = type
        this.note = note
        this.id = id
        this.date = date
    }


    fun setAmount(amount: Int){
        this.amount=amount
    }
    fun setType(Type: String?){
        type=Type
    }
    fun setId(Id: String?){
        id=Id
    }
    fun setNote(Note: String?){
        note=Note
    }
    fun setDate(Date: String?){
        date=Date
    }

    fun getAmount(): Int {
        return amount
    }
    fun getType(): String? {
        return type
    }
    fun getNote(): String? {
        return note
    }
    fun getId(): String? {
        return id
    }
    fun getDate(): String? {
        return date
    }



}
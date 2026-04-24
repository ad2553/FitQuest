package com.example.fitquest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FBRef {
    public static FirebaseAuth refAuth = FirebaseAuth.getInstance();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference refData     = FBDB.getReference("Data");
    public static DatabaseReference refUsers    = FBDB.getReference("users");
    public static DatabaseReference refTasks    = FBDB.getReference("tasks");
    public static DatabaseReference refPrograms = FBDB.getReference("programs");
    public static DatabaseReference refRecords  = FBDB.getReference("records");
}



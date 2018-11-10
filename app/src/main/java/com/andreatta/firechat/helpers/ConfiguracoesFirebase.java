package com.andreatta.firechat.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Andreatta on 21/07/2018.
 */

public final class ConfiguracoesFirebase {

   private static DatabaseReference databaseReference;
   private static FirebaseAuth firebaseAuth;

   public static DatabaseReference getFirebase(){
      if(databaseReference == null)
         databaseReference = FirebaseDatabase.getInstance().getReference();
      return databaseReference;
   }

   public static FirebaseAuth getFirebaseAuth(){
      if(firebaseAuth == null)
         firebaseAuth = FirebaseAuth.getInstance();
      return firebaseAuth;
   }
}

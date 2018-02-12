package com.tekorius.cordovaCloverPlugin;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.clover.sdk.v1.Intents;
import com.clover.sdk.v3.payments.Payment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CordovaCloverPlugin extends CordovaPlugin {

    private CallbackContext callbackContext;
    private String bill_id;

    private static final int SECURE_PAY_REQUEST_CODE = 1;
    //This bit value is used to store selected card entry methods, which can be combined with bitwise 'or' and passed to EXTRA_CARD_ENTRY_METHODS
    private int cardEntryMethodsAllowed = Intents.CARD_ENTRY_METHOD_MAG_STRIPE | Intents.CARD_ENTRY_METHOD_ICC_CONTACT | Intents.CARD_ENTRY_METHOD_NFC_CONTACTLESS | Intents.CARD_ENTRY_METHOD_MANUAL;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        this.callbackContext = callbackContext;

        if (action.equals("startPayment")) {
            Long amount = Long.parseLong(args.get(0).toString(), 10);
            startSecurePaymentIntent(amount, (String) args.get(1));

            return true;
        }

        return false;
    }

    // Start intent to launch Clover's secure payment activity
    //NOTE: ACTION_SECURE_PAY requires that your app has "clover.permission.ACTION_PAY" in it's AndroidManifest.xml file
    private void startSecurePaymentIntent(long amount, String billId) {
        Intent intent = new Intent(Intents.ACTION_SECURE_PAY);
        bill_id = billId;

        //EXTRA_AMOUNT is required for secure payment
        intent.putExtra(Intents.EXTRA_AMOUNT, amount);

        //Allow only selected card entry methods
        intent.putExtra(Intents.EXTRA_CARD_ENTRY_METHODS, cardEntryMethodsAllowed);

        //Because no order id is passed to EXTRA_ORDER_ID a new empty order will be generated for the payment
        cordova.setActivityResultCallback(this);
        cordova.getActivity().startActivityForResult(intent, SECURE_PAY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECURE_PAY_REQUEST_CODE){

            if (resultCode == Activity.RESULT_OK){
                Payment payment = intent.getParcelableExtra(Intents.EXTRA_PAYMENT);
                JSONObject json = payment.getJSONObject();

                try {
                    json.put("bill_id", bill_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callbackContext.success(json);
                Toast.makeText(cordova.getActivity().getApplicationContext(), "Payment OK", Toast.LENGTH_SHORT).show();
            } else {
                callbackContext.error(bill_id);

                Toast.makeText(cordova.getActivity().getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


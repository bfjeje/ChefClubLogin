package bruno.ellerbach.chefclublogin.API;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bruno.ellerbach.chefclublogin.model.User;

import static bruno.ellerbach.chefclublogin.common.Constants.*;

public class LoginAttempt {

    private String url = BASE_URL + API_URL;
    private Context context;
    protected RequestQueue queue;

    public LoginAttempt(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void tryToLogIn(final String email, final String password){

        Map<String, String> params = new HashMap<String, String>();
        params.put("password", password);
        params.put("email", email);


        JSONObject jsonObj = new JSONObject(params);
        Toast.makeText(context, params.toString(), Toast.LENGTH_LONG).show();

        final JsonObjectRequest json = new JsonObjectRequest(
                Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "hola", Toast.LENGTH_LONG).show();
                        User userReturned = parseResponse(response);
                        showUserMessage(userReturned);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "The status code is: " + error.networkResponse.statusCode,
                        Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(json);
    }

    private void showUserMessage(User userReturned) {

        //In this case we do nothing, but if we want, we could pass the User to the next activity
        Toast.makeText(context,
                "hello",
                Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(context)
                .setTitle("ChefsClub")
                .setMessage("Login efetuado com sucesso!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

    private User parseResponse(JSONObject response) {

        try {

            String name = response.getString("name");
            String email = response.getString("email");

            User user = new User(name, email);

            return user;

        } catch (JSONException err) {
            // Error occurred!
            Toast.makeText(context, "Json error: " + err.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }
}

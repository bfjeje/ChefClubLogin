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

    //starts the login attempt
    public LoginAttempt(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    //receive email and password, and starts queue action
    public void tryToLogIn(final String email, final String password){

        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        params.put("email", email);

        JSONObject jsonObj = new JSONObject(params);

        final JsonObjectRequest json = new JsonObjectRequest(
                Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User userReturned = parseResponse(response);
                        showSuccessMessage(userReturned);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showUnsuccessMessage();
            }
        }
        );
        queue.add(json);
    }

    //If everything is ok, it shows a successful message
    private void showSuccessMessage(User userReturned) {

        //In this case we do nothing, but if we want, we could pass the User to the next activity
        new AlertDialog.Builder(context)
                .setTitle("ChefsClub")
                .setMessage("Login efetuado com sucesso!")
                .setPositiveButton("FEITO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing for now.
                    }
                })
                .show();
    }

    //if something is wrong, it shows an unsuccessful message
    private void showUnsuccessMessage() {

        new AlertDialog.Builder(context)
                .setTitle("ChefsClub")
                .setMessage("Email ou senha invalidos!\n\n Por algum acaso esqueceu a sua senha?")
                .setPositiveButton("TENTAR NOVAMENTE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing for now.
                    }
                })
                .show();
    }

    //parse the message received
    private User parseResponse(JSONObject response) {
        try {
            String name = response.getString("name");
            String email = response.getString("email");

            User user = new User(name, email);

            return user;
        } catch (JSONException err) {
            Toast.makeText(context, "Json error: " + err.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }
}

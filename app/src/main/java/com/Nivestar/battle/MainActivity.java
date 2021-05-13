package com.Nivestar.battle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import java.util.Random;
/*
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;*/

public class MainActivity extends AppCompatActivity {

    MediaPlayer music;
    private static boolean enable_song = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        button_off();
    }

    //menu
    public void play(View view) {
        click_sound();
        setContentView(R.layout.mode);
        button_off();
    }

    public void help(View view) {
        click_sound();
        setContentView(R.layout.help);
        button_off();
        TextView text_about = (TextView) findViewById(R.id.about_game);
        String text = "     Este jogo é de apostas de combate em que as batalhas ocorrem de forma automática e aleatória." +
                " Os lutadores possuem vida própria e não precisam ser controlados.\n     Existem dois modos possíveis:\n" +
                "     No Free for All, ou cada um por si, você irá escolher um lutador dentre vários que deseja que ganhe a Arena," +
                "cada lutador possui suas próprias características, atributos, counters, passivas e especiais.\n     Após a escolha, todos os lutadores se atacaram" +
                " de forma aleatória, haverá também provabilidades de passivas, especias, eventos na arena, cotra-ataques e etc. Os lutadores que realizarem o último golpe " +
                "para eliminar outros lutadores, receberam uma pequena cura como recompensa (no modo em equipes essa cura é menor para evitar efeito bola de neve).\n" +
                "     O modo free for all termina quando apenas um lutador sobreviver!" + "Torça pelo seu lutador e boa sorte.\n\n" +
                "     O modo Batalha em equipes possui o mesmo objetivo e funcionamento do modo Free for All, porém com uma diferença,"+
                "a luta ocorrerá em equipes, ou seja, você irá escolhar um grupo com de lutadores que lutaram contra outros grupos," +
                " no final restará apenas um grupo vencedor com lutadores vivos (nos dois modos poderam ter raras situações em que todos os lutadores morrem).\n\n" + "O lutador escolhido é indicado pela inicial [E] no modo free for all.\n" +
                "No modo em grupos, cada grupo terá sua sigla em números (Ex: [2][3]...), e o grupo escolhido será indicado pela sigla numérica: [1]. A letra (P) " +
                "representa ativação da passiva do lutador, enquanto a sigla (X) indica que o lutador está impossibilitado de atacar.";
        text_about.setText(text);
    }

    public void exit(View view) {
        click_sound();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    //

    public void back(View view) {
        click_sound();
        setContentView(R.layout.menu);
        button_off();
    }

    public void start_game_ffa(View view){
        Intent intent = new Intent(MainActivity.this, Game.class);
        intent.putExtra("mode", 1);
        intent.putExtra("set", enable_song);
        startActivityForResult(intent, 1);
    }

    public void escolha_be(View view){
        click_sound();
        setContentView(R.layout.be);
        button_off();
    }

    public void eq2(View view){
        start_game_be(2);
    }
    public void eq4(View view){
        start_game_be(4);
    }
    public void eq5(View view){
        start_game_be(5);
    }
    public void eq10(View view){
        start_game_be(10);
    }

    public void start_game_be(int eq){

        Intent intent = new Intent(MainActivity.this, Game.class);
        intent.putExtra("mode", 2);
        intent.putExtra("eq", eq);
        intent.putExtra("set", enable_song);
        startActivityForResult(intent, 1);

    }

    //music
    public  void play_song(View view){
        if(view != null){
            set_enable_song();
        }
        if(music == null){
            play_song_fun();
        }else{
            stop_song();
        }
    }

    private void play_song_fun() {
        music = MediaPlayer.create(this, R.raw.menu);
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop_song();
            }
        });
        music.setLooping(true);
        music.start();
    }
    public  void stop_song(){
        if(music != null){
            music.release();
            music = null;
        }
    }

    public void click_sound(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    private void set_enable_song(){
        if(enable_song == true){
            enable_song= false;
            button_off();
        }else{
            enable_song = true;
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }
    private void button_off(){
        if(enable_song == false){
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        }else{
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                enable_song = data.getBooleanExtra("result",false);
            }else{
                enable_song = true;
            }
        }
        button_off();
    }

    @Override
    protected void onStop(){
        super.onStop();
        stop_song();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(enable_song==true){
            play_song(null);
        }
    }
    protected void onRestart() {
        super.onRestart();
        enable_song = false;
        if (enable_song == true) {
            play_song(null);
        }
    }

}
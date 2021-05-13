package com.Nivestar.battle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;

import static android.widget.Toast.*;

public class Game extends AppCompatActivity {

    static Random rand = new Random();
    static int total = 20; // numero de fighters (deve ser multiplo de 5 por enquanto)
    static int ordem = 1;
    private static Fighter[] fighter = new Fighter[total+1];
    private static boolean enable_song;
    MediaPlayer music;
    static int mode;
    static int eq;
    static int qtd_eq;
    static int n_games;
    static int top1;
    static int top2;
    static int top3;
    static int id_top1;
    static int id_top2;
    static int id_top3;
    static int winrate[] = new int[20];
    static int damage[] = new int[20];
    static int escolhas_restantes;
    static int escolhas_restantes2;
    static int escolhas_restantes3;
    static int escolhas_restantes4;
    static int escolhas_restantes5;
    static int escolhas_restantes6;
    static int escolhas_restantes7;
    static int escolhas_restantes8;
    static int escolhas_restantes9;
    static int escolhas_restantes10;
    static int rodada;

    static String allfight = "----------Start----------\n";

    static boolean automatic = false;
    static int current_choose = 0;
    static String especiais_historico = "";

    // special variables
    static int goblin = 0;
    static int escudeiro = 0;
    static int arqueiro = 0;
    static int berserker = 0;
    static int cacador = 0;
    static int ninja = 0;
    static int viking = 0;

    static Handler handler = new Handler(Looper.getMainLooper());

    public void remove_chamadas(){
        handler.removeCallbacks(runnable1);
        handler.removeCallbacks(runnable2);
        handler.removeCallbacks(runnable3);
        handler.removeCallbacks(runnable4);
        handler.removeCallbacks(btrunnable2);
        handler.removeCallbacks(btrunnable);
    }

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            if(automatic)
                states(null);
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            if(automatic)
                states2(null);
        }
    };
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            if(automatic)
                states3(null);
        }
    };
    Runnable runnable4 = new Runnable() {
        @Override
        public void run() {
            if(automatic)
                states4(null);
        }
    };
    Runnable btrunnable = new Runnable() {
        @Override
        public void run() {
            if(automatic)
                Battle(null);
        }
    };
    Runnable btrunnable2 = new Runnable() {
        @Override
        public void run() {
            if(automatic)
                Battle2(null);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // tela sempre ligada

        allfight = "";
        automatic = false;
        current_choose = 0;
        especiais_historico = "";
        escolhas_restantes = -1;
        escolhas_restantes2 = -1;
        escolhas_restantes3 = -1;
        escolhas_restantes4 = -1;
        escolhas_restantes5 = -1;
        escolhas_restantes6 = -1;
        escolhas_restantes7 = -1;
        escolhas_restantes8 = -1;
        escolhas_restantes9 = -1;
        escolhas_restantes10 = -1;

        for(int l=0; l<total; l++){
            winrate[l] = 0;
        }
        // special variables
        goblin = 0;
        escudeiro = 0;
        arqueiro = 0;
        berserker = 0 ;
        cacador = 0;
        ninja = 0;
        viking = 0;
        rodada = 0;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            enable_song = true;
            mode = 1;
            makeText(getApplicationContext(), "null!!", LENGTH_SHORT).show();
        }else {
            enable_song = extras.getBoolean("set");
            mode = extras.getInt("mode", 1);
            eq = extras.getInt("eq", 5);
        }

        for(int i=0; i<total; i++) //zerando dano
            damage[i] = 0;

        for (int i = 0; i < total+1; i++)
            fighter[i] = new Fighter(i, -1);

        load(); //load game

        if(mode == 1) {
            escolhas_restantes = 1;
            select_show(null);

        }else if (mode == 2){
            switch (eq){
                case 2:
                    escolhas_restantes = 2;
                    escolhas_restantes2 = 1;
                    escolhas_restantes3 = 1;
                    escolhas_restantes4 = 1;
                    escolhas_restantes5 = 1;
                    escolhas_restantes6 = 1;
                    escolhas_restantes7 = 1;
                    escolhas_restantes8 = 1;
                    escolhas_restantes9 = 1;
                    escolhas_restantes10 = 1;
                    qtd_eq = 10;
                    break;
                case 4:
                    escolhas_restantes = 4;
                    escolhas_restantes2 = 1;
                    escolhas_restantes3 = 1;
                    escolhas_restantes4 = 1;
                    escolhas_restantes5 = 1;
                    qtd_eq = 5;
                    break;
                case 5:
                    escolhas_restantes = 5;
                    escolhas_restantes2 = 1;
                    escolhas_restantes3 = 1;
                    escolhas_restantes4 = 1;
                    qtd_eq = 4;
                    break;
                case 10:
                    escolhas_restantes = 10;
                    escolhas_restantes2 = 1;
                    qtd_eq = 2;
            }
            select_show(null);
        }else{
            makeText(getApplicationContext(), "modeee", LENGTH_SHORT).show();
            setContentView(R.layout.end);
        }
    }

    public void details( View view) {
        setContentView(R.layout.details);
        button_off();
        TextView text_event;
        //especial
        text_event = findViewById(R.id.special);
        String text = "Especial: " + especial_fighter();
        text_event.setText(text);
        //passiva
        text_event = findViewById(R.id.passiva);
        text = "     Passiva\n" + passiva_fighter();
        text_event.setText(text);
        //name
        text_event = findViewById(R.id.lutador_nome);
        text = fighter[current_choose].get_name() + " (" + (current_choose + 1) + "/20)";
        text_event.setText(text);
        //class
        text_event = findViewById(R.id.cls);
        text = fighter[current_choose].get_cls();
        text_event.setText(text);
        //imagem
        image_fighter();
    }

    public void select_show( View view){
        setContentView(R.layout.select);
        button_off();
        TextView text_event;
        String text;
        if(escolhas_restantes != 0){
            //select
            text_event = findViewById(R.id.escolha);
            if(mode == 1){
                text = "Escolha de lutador:";
            }else {
                if (escolhas_restantes10 == 0)
                    text = "Escolha de lutador:(T10:" + escolhas_restantes + ")";
                else if (escolhas_restantes9 == 0)
                    text = "Escolha de lutador:(T9:" + escolhas_restantes + ")";
                else if (escolhas_restantes8 == 0)
                    text = "Escolha de lutador:(T8:" + escolhas_restantes + ")";
                else if (escolhas_restantes7 == 0)
                    text = "Escolha de lutador:(T7:" + escolhas_restantes + ")";
                else if (escolhas_restantes6 == 0)
                    text = "Escolha de lutador:(T6:" + escolhas_restantes + ")";
                else if (escolhas_restantes5 == 0)
                    text = "Escolha de lutador:(T5:" + escolhas_restantes + ")";
                else if (escolhas_restantes4 == 0)
                    text = "Escolha de lutador:(T4:" + escolhas_restantes + ")";
                else if (escolhas_restantes3 == 0)
                    text = "Escolha de lutador:(T3:" + escolhas_restantes + ")";
                else if (escolhas_restantes2 == 0)
                    text = "Escolha de lutador:(T2:" + escolhas_restantes + ")";
                else
                    text = "Escolha de lutador:(T1:" + escolhas_restantes + ")";
            }
            text_event.setText(text);
            //name
            text_event = findViewById(R.id.lutador_nome);
            text = fighter[current_choose].get_name() + " (" + (current_choose + 1) + "/20)";
            text_event.setText(text);
            //class
            text_event = findViewById(R.id.cls);
            text = fighter[current_choose].get_cls();
            text_event.setText(text);
            //imagem
            image_fighter();
            //status
            text_event = findViewById(R.id.status_lutador);
            if(current_choose == 19){
                text = "HP:  ? " + "\n\nAtaque:  ?" + "\n\n" + "Especial:  " + fighter[current_choose].get_special()
                        + "\n\n" + "Passiva:  " + fighter[current_choose].get_passiva();
            }else {
                text = "HP: " + fighter[current_choose].get_hp_max() + "\n\nAtaque: " +
                        fighter[current_choose].get_at_min() + "-" + fighter[current_choose].get_at_max() + "\n\n" +
                        "Especial:  " + fighter[current_choose].get_special() + "\n\n" +
                        "Passiva:  " + fighter[current_choose].get_passiva();
            }
            text_event.setText(text);
            //counters
            text_event = findViewById(R.id.desc);
            text = " " + fighter[current_choose].get_counters() + "\n";
            //winrate
            int porc = 0;
            if (n_games > 0)
                porc = (winrate[current_choose]) * 100 / n_games;
            text = text + "Winrate: " + winrate[current_choose] + " vitórias\n " + n_games + " batalhas (" + porc + "%).\n";
            //top dano
            if (current_choose == id_top1)
                text = text + "1º top dano = " + top1 + "\n";
            if (current_choose == id_top2)
                text = text + "2º top dano = " + top2 + "\n";
            if (current_choose == id_top3)
                text = text + "3º top dano = " + top3 + "\n";

            text_event.setText(text);

        }else {
            if(mode == 2) {
                if (escolhas_restantes2 > 0) {
                    escolhas_restantes2 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                } else if (escolhas_restantes3 > 0) {
                    escolhas_restantes3 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                } else if (escolhas_restantes4 > 0) {
                    escolhas_restantes4 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }else if (escolhas_restantes5 > 0) {
                    escolhas_restantes5 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }
                else if (escolhas_restantes6 > 0) {
                    escolhas_restantes6 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }else if (escolhas_restantes7 > 0) {
                    escolhas_restantes7 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }else if (escolhas_restantes8 > 0) {
                    escolhas_restantes8 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }
                else if (escolhas_restantes9 > 0) {
                    escolhas_restantes9 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }else if (escolhas_restantes10 > 0) {
                    escolhas_restantes10 = 0;
                    escolhas_restantes = eq;
                    select_show(null);
                }
                else{
                    ordem = 1;
                    for(int k=0; k< total; k++) //colocando simbolos de team
                        fighter[k].classificar(mode);
                    states(null);
                }
            }else{
                for(int i=0; i<total; i++){
                    if(fighter[i].get_team() == -1)
                        fighter[i].set_team(2);
                }
                ordem = 1;
                for(int k=0; k< total; k++) //colocando simbolos de team
                    fighter[k].classificar(mode);
                states(null);
            }
        }
    }

    public void make_states( int n){
        int i, j, temp=0, showed = 0, equipe=0;
        if (mode == 1){ //free for all
            for (i=1,j = n; i<= 5; i++, j++) {
                coloring_ffa(i, j);
            }
        }
        if (mode == 2){ //luta em grupo
            switch (n){
                case 0: //primeiro status
                    temp = eq;
                    equipe = 1;
                    break;
                case 5: //segundo status
                    switch (eq){  //quantos lutadores em cada equipe
                        case 2:
                            showed = 1; //quantos dessa equipe ja foram mostrados no status anterior e devem ser pulados
                            equipe = 3; // qual equipe sera mostrada
                            break;
                        case 4:
                            showed = 1;
                            equipe = 2;
                            break;
                        case 5:
                            equipe = 2;
                            break;
                        case 10:
                            showed = 5;
                            equipe = 1;
                    }
                    break;
                case 10: //terceiro status
                    switch (eq){
                        case 2:
                            equipe = 6;
                            break;
                        case 4:
                            showed = 2;
                            equipe = 3;
                            break;
                        case 5:
                            equipe = 3;
                            break;
                        case 10:
                            equipe = 2;
                    }
                    break;
                case 15: //quarto status
                    switch (eq) {
                        case 2:
                            showed = 1;
                            equipe = 8;
                            break;
                        case 4:
                            showed = 3;
                            equipe = 4;
                            break;
                        case 5:
                            equipe = 4;
                            break;
                        case 10:
                            showed = 5;
                            equipe = 2;
                    }
            }
            temp = eq - showed; //quantos faltam mostrar(temp) = o total(eq) -  quantidade ja mostrada(showed)

            for (j=0, i=1; i<= 5; j++) { //for para todos os 5 espaços a serem mostrados

                if(temp == 0){  //ja mostrou todos dessa equipe e ainda tem espaço pra mostrar mais
                    temp=eq;
                    equipe++;  //passa pra proxima equipe
                    j=0;
                }
                if(j==total){ //reiniciando id lutador
                    j = 0;
                }
                if(fighter[j].get_team() == equipe) {
                    if (showed > 0) {  //pular pq ja foi mostrado no outro status
                        showed--;
                    } else {
                        coloring_be(i, j); //mostrar
                        i++;
                        temp--;
                    }
                }

            }
        }
        TextView text_event = findViewById(R.id.round);;
        text_event.setText("Rodada " + (rodada+1));
    }

    public String Choose_Special(){
        String texto = "";
        int r, lutador, dmg;
        do {
            lutador = rand.nextInt(total);
        }while(check_dead(lutador) ); //&& lutador == 0 && lutador == 0 && lutador != 0 && lutador != 0);
        switch (lutador){
            case 0: //goblin
                texto = texto + fighter[lutador].get_name() + " utiliza um veneno fazendo todos os lutadores dormirem até final da rodada.";
                goblin += 1;
                break;
            case 1: //escudeiro
                texto =  texto + fighter[lutador].get_name() + " utiliza de sua perícia com o escudo que lhe permite bloquear totalmente os próximos 2 ataques dos lutadores.";
                escudeiro += 2;
                fighter[lutador].set_especial(true);
                break;
            case 2: //mago
                texto = texto + fighter[lutador].get_name() + " realiza um feitiço de chuva de bolas de fogo em toda arena causando 15 de dano a todos os outros lutadores.";
                texto = texto + area_attack(15,19,2);
                break;
            case 3: //assassino
                r = find_live_enemy(3);
                texto = texto + fighter[lutador].get_name() + " desfere um golpe mortal em " + fighter[r].get_name() + ".";
                texto = texto + fight_damage(3, r, fighter[r].get_current_hp());
                break;
            case 4: //arqueira
                if(arqueiro > 0){
                    for (int i = 0; i < total; i++) {
                        if (fighter[i].get_no_attack())
                            fighter[i].set_no_attack(false);
                    }
                }
                arqueiro = 2;
                r = find_live_enemy(4);
                texto = texto + fighter[lutador].get_name() + " acerta uma flecha paralisante no lutador " + fighter[r].get_name() + " impossibilitando de atacar por duas rodadas.";
                fighter[r].set_no_attack(true);
                break;
            case 5: //viking
                texto = texto + fighter[lutador].get_name() + " recebe a benção de Odin e irá revidar todos os ataques por duas rodadas.";
                viking += 2;
                fighter[lutador].set_especial(true);
                break;
            case 6: //berserker
                texto = texto + fighter[lutador].get_name() + " se enfurece e seus próximos 2 ataques serão críticos causando o dobro de dano.";
                berserker += 2;
                fighter[lutador].set_especial(true);
                break;
            case 7: //bruxa
                r = find_live_enemy(7);
                texto = texto + fighter[lutador].get_name() + " absorve a vida de " + fighter[r].get_name() + " causando 25 de dano a ele e recuperando 25 pontos de vida.";
                fighter[7].healing(25);
                texto = texto + fight_damage(7, r,25);
                break;
            case 8: //lanceiro
                r = find_live_enemy(8);
                int r2 = find_live_enemy(8);
                texto = texto + fighter[lutador].get_name() + " desfere 2 golpes atingindo " + fighter[r].get_name() + " e " + fighter[r2].get_name() + ", causando 20 de dano e afiando a lança(P) em cada golpe.";
                texto = texto + fight_damage(8, r,20);
                texto = texto + fight_damage(8, r2,20);
                fighter[8].set_at_max(fighter[8].get_at_max() + 2);
                fighter[8].set_at_min(fighter[8].get_at_min() + 2);
                break;
            case 9: //curandeira
                texto = texto + fighter[lutador].get_name() + " conjura uma super cura para todos seus ferimentos, recuperando todos os pontos de vida perdidos.";
                fighter[9].healing(1000);
                break;
            case 10: //orc
                r = find_live_enemy(10);
                dmg = rand.nextInt(60 - 30 + 1 ) + 30; // entre 30 a 60 de dano
                texto = texto + fighter[lutador].get_name() + " arremessa uma grande lamina que atinge " + fighter[r].get_name() + " causando " + dmg + " de dano (varia).";
                texto = texto + fight_damage(10,r,dmg);
                break;
            case 11: //monge
                texto = texto + fighter[lutador].get_name() + " consegue um momento para meditar fortalecendo seus pontos de ataque máximo e mínimo em 10 pontos permanentemente.";
                fighter[11].set_at_max(fighter[11].get_at_max() + 10);
                fighter[11].set_at_min(fighter[11].get_at_min() + 10);
                break;
            case 12: //caçador
                texto = texto +fighter[lutador].get_name() + " invoca um animal feroz que atacará um inimigo pelas próximas 3 batalhas causando de 10 a 20 de dano.";
                cacador = 3;
                fighter[lutador].set_especial(true);
                break;
            case 13: //paladino
                texto = texto + fighter[lutador].get_name() + " realiza uma magia de cura recuperando 15 de vida, sua força física é revigorada aumentando seu ataque mínimo e máximo em 5.";
                fighter[13].healing(15);
                fighter[13].set_at_max(fighter[13].get_at_max()+5);
                fighter[13].set_at_min(fighter[13].get_at_min()+5);
                break;
            case 14: //samurai
                r= find_live_enemy(14);
                int porc = 7*rodada;
                if(porc > 91)
                    porc = 91;
                dmg = Math.round( (porc*fighter[r].get_current_hp()) / 100 );
                texto = texto +fighter[lutador].get_name() + " realiza um ataque que utiliza da força vital do "  + fighter[r].get_name() + " contra ele, causando " + porc
                        + "% de sua vida ("  + dmg + ") como dano.";
                break;
            case 15://druida
                texto = texto + fighter[lutador].get_name() + " extrai energia da natureza ganhando 30 pontos de vida permanentemente.";
                fighter[15].set_hp_max(fighter[15].get_hp_max()+ 30);
                fighter[15].healing(30);
                break;
            case 16://gladiador
                texto = texto + fighter[lutador].get_name() + " utiliza de sua experiência com batalhas numerosas e golpeia 3 inimigos ao mesmo tempo com uma maça, causando 25 de dano em área.";
                texto = texto + area_attack(20,3,16);
                break;
            case 17://urso gigante
                r= find_live_enemy(17);
                texto = texto + fighter[lutador].get_name() + " abraça "+ fighter[r].get_name() + " causando 50 de dano.";
                texto = texto + fight_damage(17,r, 50);
                break;
            case 18://ninja
                texto = texto + fighter[lutador].get_name() + " se camufla nas sombras e não sofrerá ataques por 2 rodadas.";
                ninja += 2;
                fighter[lutador].set_especial(true);
                break;
            case 19://lutador misterioso
                texto =  texto + fighter[lutador].get_name() + " retira uma foice e executa um corte giratório causando 15 de dano em área a 5 lutadores ao redor e enfraque-os.";
                texto = texto + area_attack(12,5,19);
                break;
        }
        especiais_historico += fighter[lutador].get_name() + ". ";
        return texto;
    }

    public String action(){
        String texto = "";
        String texto2 = "";
        int id_at = -1, id_def= -1, r, dmg=0, m, x;

        //sorteando atacante ####################################
        do{
            id_at = rand.nextInt(total+1);
            if(id_at == total && !check_dead(17)){
                texto = "(Passiva Urso usada).\n";
                if(fighter[17].get_at_max() > 10){
                    fighter[17].set_at_max(fighter[17].get_at_max()-2);
                    if(fighter[17].get_at_max() < 10){
                        fighter[17].set_at_max(10);
                    }
                }
                id_at = 17;
            }
        }while(check_dead(id_at));

        //sorteando defesa ####################################
        if(mode == 1) {
            do {
                id_def = rand.nextInt(total);
                if(id_def == 12 && !check_dead(12)){ //passiva caçador
                    r = rand.nextInt(100);
                    if(r < 20){
                        texto2 = "(Passiva Caçador usada).\n";
                        id_def = rand.nextInt(total);
                    }
                }
            } while ((id_at == id_def) || (check_dead(id_def)));

        }else if(mode ==2) {
            do {
                id_def = rand.nextInt(total);
                if(id_def == 12 && !check_dead(12)){ //passiva caçador
                    r = rand.nextInt(100);
                    if(r < 20){
                        texto2 = "(Passiva do caçador usada).\n";
                        id_def = rand.nextInt(total);
                    }
                }
            } while (check_dead(id_def) || !is_enemy(id_at, id_def));
        }
        texto = texto + texto2; //solucao para a repeticao

        //ataques ###########################33

        if(check_arqueiro(id_at)) {
            texto = texto + fighter[id_at].get_name() + " não conseguiu atacar " + fighter[id_def].get_name() + " por causa da flecha envenenada da " + fighter[4].get_name();
            return texto;
        }

        r = rand.nextInt(100);
        if(id_at == 13 && r<15){
            dmg = (int) fighter[id_at].attack()/2;
            texto = texto + fighter[id_at].get_name() + " cancelou seu ataque e se curou em " + dmg + " pontos(P).";
            fighter[id_at].healing(dmg);
            return  texto;
        }
        //especiais
        if (check_ninja(id_def)) {
            texto = texto + fighter[id_at].get_name() + " tentou atacar " + fighter[18].get_name() + " mas ele desapareceu nas sombras.";
            return texto;
        }
        if (check_escudeiro(id_def)) { //escudeiro especial
            texto = texto + fighter[id_at].get_name() + " atacou " + fighter[1].get_name() + " mas ele bloqueou o ataque.";
            return texto;
        }
        if (check_berserker(id_at)) { //berserker especial
            dmg = (fighter[id_at].attack()) * 2;
            texto = texto + fighter[id_at].get_name() + " está enfurecido e acertou um critico em " + fighter[id_def].get_name() + " causando " + dmg + " de dano";
            if(id_def == 1 && dmg > 25){
                dmg = 25;
                texto = texto + " " + fighter[id_def].get_name() + " reduziu o dano para 25(P).";
            }
            if(fighter[id_at].get_counterID() == id_def){ //counter
                texto = texto + " +5 de dano(Counter). ";
                dmg += 5;
            }

        }else{ //atacante normal

            dmg = fighter[id_at].attack();
            texto = texto + fighter[id_at].get_name() + " atacou " + fighter[id_def].get_name() + " causando " + dmg + " de dano.";

            if(id_def == 1 && dmg > 25){
                dmg = 25;
                texto = texto + " " + fighter[id_def].get_name() + " reduziu o dano para 25(P).";
            }

            if(id_at == 14 && r<(5+rodada) ){
                int passiva_dano = fighter[id_at].attack();
                texto += " " + fighter[id_at].get_name() + " acertou outro corte em seu ataque causando mais " + passiva_dano + " de dano adicional(P).";
                dmg += passiva_dano;
            }

            if(fighter[id_at].get_counterID() == id_def){ //counter
                texto = texto + " +5 de dano(Counter). ";
                dmg += 5;
            }

            if(id_at == 19){
                texto = texto + " "+fighter[id_at].get_name() + " enfraquece sua vítima(P).";
                if(fighter[id_def].get_at_max() > 10){
                    fighter[id_def].set_at_max( fighter[id_def].get_at_max() -1 );
                }
                if(fighter[id_def].get_at_min() > 10){
                    fighter[id_def].set_at_min( fighter[id_def].get_at_min() -1 );
                }
            }

            if(id_at == 0){ //goblin passiva
                x = ( fighter[id_def].get_at_max() + fighter[id_def].get_at_min() ) / 2;
                x = Math.round( (x * 25/ 100 ) - 2);
                if(x<1)
                    x =1;
                texto = texto + " Veneno causa " + x + " de dano adicional(P).";
                dmg += x;
            }

            if(id_at == 7){ //bruxa
                x = Math.round( (fighter[id_def].get_hp_max() * 7 / 100) - 4);
                texto = texto + " Se curando  em " + x + " pontos(P).";
                fighter[7].healing(x);
            }
            if(id_at == 8) { //lanceiro
                texto = texto + " Lança foi afiada(P).";
                fighter[8].set_at_max(fighter[8].get_at_max() + 1);
                fighter[8].set_at_min(fighter[8].get_at_min() + 1);
            }
        }
        // dano e morte ########################################
        damage[id_at] += dmg;
        m = fighter[id_def].receive_dmg(dmg, id_at, ordem);
        if(id_at == 2 && r < 15){ //passiva mago
            texto = texto +  fighter[id_at].get_name() + " acertou um relampago em um lutador durante seu ataque causando 25 de dano(P). ";
            texto = texto + area_attack(25,1,2);
        }
        if(id_at == 16 && r < 20 && count_live() > 2) { // passiva gladiador
            texto = texto + fighter[id_at].get_name() +"  acertou outro lutador com seu ataque(P) causando o mesmo dano. ";
            texto = texto + area_attack(dmg,1,16);
        }
        if(m == 0 && id_at == 3 && fighter[id_def].get_current_hp() <= 5){ //assassino passiva
            texto = texto  + fighter[id_at].get_name() + " executou " + fighter[id_def].get_name() + " que estava gravemente ferido(P). ";
            damage[id_at] += fighter[id_def].get_current_hp();
            fighter[id_def].receive_dmg( fighter[id_def].get_current_hp(), id_at , ordem);
            m = 1;
        }
        if(m == 2){
            texto = texto + "\n" + fighter[id_def].get_name() + " morreu pela primera vez e ressucitou com 1 de HP(P). ";
        }
        //morte
        if (m == 1) {
            texto = texto + "\n[" + fighter[id_at].get_name() + " matou " + fighter[id_def].get_name() + ".]";
            if(id_at == 11){
                texto = texto + "\n" + fighter[id_at].get_name() + " aumentou seu ataque(P).";
                fighter[11].set_at_max( fighter[11].get_at_max() + 3);
                fighter[11].set_at_min( fighter[11].get_at_min() + 3);
            }
            if(fighter[id_def].get_team() == 1){
                texto = texto + "\nLutador escolhido foi morto!";
            }
            if(mode == 1){
                fighter[id_at].healing(5);
            }else{
                fighter[id_at].healing(2);
            }
            fighter[id_at].kills();
            ordem++;
            if(ordem == 15){
                fighter[0].passiva_gob();
            }
        } else {  // revidarr ##################################################
            r = rand.nextInt(100);
            texto = texto + " ";
            if(check_viking(id_def)) { // viking
                texto = texto + fighter[id_def].get_name() + " está revidando todos os ataques.\n";
                r = 100;
            }

            if (r >= 50) { //revidar
                if(id_at == 18 && r < 75) { //ninja pasiva
                    texto = texto + fighter[id_def].get_name() + " tentou revidar mas não conseguiu(P).";
                    return texto;
                }
                if(check_arqueiro(id_def)){
                    texto = texto + fighter[id_def].get_name() + " não conseguiu revidar por causa da flecha envenenada da Arqueira";
                    return texto;
                }
                if (check_escudeiro(id_at)) {
                    if(!check_viking(id_def)){
                        texto = texto + fighter[id_at].get_name() + " bloqueou o revide de " + fighter[id_def].get_name();
                        return texto;
                    }
                }

                int dmg2 = fighter[id_def].attack();

                if(id_at == 1 && dmg2 > 25){
                    texto = texto + " " + fighter[id_at].get_name() + " reduziu o dano de " + dmg2 + " para 25(P).";
                    dmg2 = 25;
                }

                texto = texto + " " + fighter[id_def].get_name() + " revidou causando " + dmg2 + " de dano!";

                if(id_def == 8) {
                    texto = texto + " Lança foi afiada(P).";
                    fighter[8].set_at_max(fighter[8].get_at_max() + 1);
                    fighter[8].set_at_min(fighter[8].get_at_min() + 1);
                }
                if(id_def == 5){  //viking  samurai 14
                    texto += " Curou em 5 pontos por revidar(P).";
                    fighter[id_def].healing(5);
                }
                texto = texto + fight_damage(id_def, id_at, dmg2);
            }
        }
        return texto;
    }

    public void Battle (View view){
        remove_chamadas();
        TextView text_event;
        click_sound2();
        setContentView(R.layout.battle);
        button_off();
        visualAutomatic();
        rodada ++;
        String texto = "{Rodada " + rodada + "}\n";
        String texto_aux = "";
        text_event = findViewById(R.id.ato1);

        int live = count_live(), event= 0, max_event = 4;
        int luck = rand.nextInt(3);  //chances de um especial ocorrer

        if (mode == 1){
            do {
                if(event == 1){
                    if (cacador > 0) { //ataque do special do caçador
                        int r = find_live_enemy(12);
                        int dmg = rand.nextInt(20 - 10 + 1) + 10;
                        texto = texto + "A fera que o " + fighter[12].get_name() + " invocou atacou " + fighter[r].get_name() + " causando " + dmg + " de dano. ";
                        texto = texto + fight_damage(12, r, 10) + "\n\n";
                        cacador--;
                        if(cacador == 0)
                            fighter[12].set_especial(false);
                    }
                }
                if (event == 0  && live > 4 && luck == 0) {
                    texto_aux = Choose_Special() + "\n\n";
                    if(goblin > 0){ //no caso do globin o texto do especial ficara no final
                        texto = texto + action() + "\n\n";
                    }else{
                        texto = texto + texto_aux;
                    }
                }else {
                    if(event == 3 && goblin > 0){
                        texto = texto + texto_aux;
                    }else{
                        texto = texto + action() + "\n\n";
                    }
                }
                live = count_live();
                event++;
            } while (live > 1 && event < max_event);

            if (live == 1){
                end_game(texto);
            }else{
                if(live == 0){
                    makeText(getApplicationContext(), "Eita ninguem viveu", LENGTH_SHORT).show();
                }else{
                    allfight += texto;
                    text_event.setText(texto);
                }
            }
        }else if (mode == 2){
            do {
                if(event == 1){
                    if (cacador > 0) { //ataque do special do caçador
                        int r = find_live_enemy(12);
                        texto = texto + "A fera que o " + fighter[12].get_name() + " invocou atacou " + fighter[r].get_name() + " causando 15 de dano. ";
                        texto = texto + fight_damage(12, r, 15) + "\n\n";
                        cacador--;
                        if(cacador == 0)
                            fighter[12].set_especial(false);
                    }
                }
                if (event == 0  && live > 4 && luck == 0) {
                    texto = texto + Choose_Special() + "\n\n";
                } else {
                    texto = texto + action() + "\n\n";
                }
                event++;
            } while (not_single_equip() && event < max_event);

            if (!not_single_equip()){
                live = count_live();
                if(live == 0){
                    makeText(getApplicationContext(), "Eita ninguem viveu", LENGTH_SHORT).show();
                }else{
                    end_game(texto);
                }
            }else{
                allfight += texto;
                text_event.setText(texto);
            }
        }
        handler.postDelayed(btrunnable2, 6000);
    }

    public void Battle2(View view){
        remove_chamadas();
        TextView text_event;
        click_sound();
        setContentView(R.layout.battle2);
        button_off();
        visualAutomatic();
        text_event = findViewById(R.id.ato4);
        String texto = "";
        int r, dmg, x;
        int live = count_live(), event= 0, max_event = 4;
        int luck = rand.nextInt(6); //chances de eventos da arena
        if(mode == 1){
            if(goblin>0){
                texto = texto + "Enquanto os lutadores dormiam " + fighter[0].get_name() + " desferiu ataques:\n\n";
                for(int i =0; i<max_event && live>1 ; i++) {
                    r = find_live_enemy(0);
                    dmg = fighter[0].attack();
                    texto = texto + fighter[0].get_name() + " atacou " + fighter[r].get_name() + " causando " + dmg + " de dano.";
                    if(r == fighter[0].get_counterID()){
                        texto = texto + " Causa 5 de dano adicional(Counter).";
                        dmg += 5;
                    }
                    //goblin passiva
                    x = ( fighter[r].get_at_max() + fighter[r].get_at_min() ) / 2;
                    x = Math.round( (x * 25/ 100 ) - 2);
                    if(x<1)
                        x =1;
                    texto = texto + " Veneno causa " + x + " de dano adicional(P).";
                    dmg += x;
                    texto = texto + fight_damage(0, r, dmg) + "\n\n";
                    live = count_live();
                }
                goblin--;
            }else {
                while (live > 1 && event < max_event) {
                    if (event == 0 && luck == 0) {
                        texto = texto + random_event() + "\n\n";
                    } else {
                        texto = texto + action() + "\n\n";
                    }
                    live = count_live();
                    event++;
                }
                //fim da rodada
                if(ninja > 0){
                    ninja--;
                    if(ninja == 0)
                        fighter[18].set_especial(false);
                }
                if (arqueiro > 0) {
                    arqueiro--;
                    if(arqueiro == 0){
                        for (int i = 0; i < total; i++) {
                            if (fighter[i].get_no_attack())
                                fighter[i].set_no_attack(false);
                        }
                    }
                }
                if(viking > 0){
                    viking--;
                    if(viking == 0)
                        fighter[5].set_especial(false);
                }
                if(!check_dead(15)){ //passiva druida
                    fighter[15].set_hp_max( fighter[15].get_hp_max()+2);
                    fighter[15].healing(2);
                }
                if(!check_dead(11)) {//passiva monge
                    if(fighter[11].get_at_max() > 10 && fighter[11].get_at_min() > 10)
                        fighter[11].set_at_max( fighter[11].get_at_max()-1);
                        fighter[11].set_at_min( fighter[11].get_at_min()-1);
                }
                if(!check_dead(7)) {//passiva bruxa
                    int m = fighter[7].receive_dmg(3,7,ordem);
                    if(m == 1) {
                        texto += "[Bruxa se matou(P).]";
                        ordem++;
                        if(ordem == 15){
                            fighter[0].passiva_gob();
                        }
                    }
                }
            }

            if (live == 1){
                end_game(texto);
            }else{
                if(live == 0){
                    makeText(getApplicationContext(), "Eita ninguem viveu", LENGTH_SHORT).show();
                    end_game(texto);
                }else{
                    allfight += texto;
                    text_event.setText(texto);
                }
            }
        }else if (mode == 2){
            if(goblin>0){
                texto = texto + "Enquanto os lutadores dormiam " + fighter[0].get_name() + " desferiu ataques:\n";
                for(int i =0; i<max_event && not_single_equip() ; i++) {
                    do {
                        r = find_live_enemy(0);
                    }while(fighter[r].get_team() == fighter[0].get_team());
                    dmg = fighter[0].attack();
                    if(r == fighter[0].get_counterID()){
                        texto = texto + " Causa 5 de dano adicional(Counter).";
                        dmg += 5;
                    }
                    texto = texto + fighter[0].get_name() + " atacou " + fighter[r].get_name() + " causando " + dmg + " de dano.";
                    //goblin passiva
                    x = fighter[r].get_hp_max() * 2 / 100;
                    texto = texto + " Veneno causa " + x + " de dano adicional(P).";
                    dmg += x;
                    texto = texto + fight_damage(0, r, dmg) + "\n\n";
                }
                goblin--;
                //nao pronto ainda
            }else {
                while (not_single_equip() && event < max_event) {
                    if (event == 0 && luck == 0) {
                        texto = texto + random_event() + "\n\n";
                    } else {
                        texto = texto + action() + "\n\n";
                    }
                    event++;
                }
                //fim da rodada
                if(ninja > 0){
                    ninja--;
                    if(ninja == 0)
                        fighter[18].set_especial(false);
                }
                if (arqueiro > 0) {
                    arqueiro--;
                    if(arqueiro == 0){
                        for (int i = 0; i < total; i++) {
                            if (fighter[i].get_no_attack())
                                fighter[i].set_no_attack(false);
                        }
                    }
                }
                if(viking > 0){
                    viking--;
                    if(viking == 0)
                        fighter[5].set_especial(false);
                }
                if(!check_dead(15)){ //passiva druida
                    fighter[15].set_hp_max( fighter[15].get_hp_max()+2);
                    fighter[15].healing(2);
                }
                if(!check_dead(11)) {//passiva monge
                    if(fighter[11].get_at_max() > 10)
                        fighter[11].set_at_max( fighter[11].get_at_max()-1);
                    fighter[11].set_at_min( fighter[11].get_at_min()-1);
                }
                if(!check_dead(7)) {//passiva bruxa
                    int m = fighter[7].receive_dmg(3,7,ordem);
                    if(m == 1) {
                        texto += "[Bruxa se matou(P).]";
                        ordem++;
                        if(ordem == 15){
                            fighter[0].passiva_gob();
                        }
                    }
                }
            }
            if (!not_single_equip()){
                live = count_live();
                if(live == 0){
                    makeText(getApplicationContext(), "Eita ninguem viveu", LENGTH_SHORT).show();
                }else{
                    end_game(texto);
                }
            }else{
                allfight += texto;
                text_event.setText(texto);
            }
        }
        handler.postDelayed(runnable1, 6000);
    }

    public void end_game(String texto){
        if(automatic)
            automatic = false;
        remove_chamadas();
        setContentView(R.layout.end);
        button_off();
        TextView text_event;
        text_event = findViewById(R.id.ato);
        int winner = -1;

        if (mode == 1){
            for (int i = 0; i < total; i++) {
                if (!check_dead(i)) {
                   winner = i;
                }
            }
            if(winner == -1) { //ninguem sobreviveu
                texto = texto + " Ninguem sobreviveu, não haverá premiação sobrevivente";
            }
            texto = texto + " O vencedor e único sobrevivente é o lutador " + fighter[winner].get_name() +
                    " com " + fighter[winner].get_current_hp() + " pontos de vida!";
            winrate[winner]++;
        }
        else if (mode == 2){
            int winners[] = new int[eq];
            for(int i=0 ; i<eq; i++){
                winners[i]= -1;
            }
            int tam = 0;
            for (int i = 0; i < total; i++) {
                if (!check_dead(i)) {
                    winners[tam] = i;
                    tam++;
                }
            }
            if(tam == 0){
                texto = texto + " Ninguem sobreviveu, não haverá premiação sobrevivente";

            }else{
                winner = -2;
                texto = texto + " A equipe vencedora é a equipe " + fighter[winners[0]].get_team() +
                        " com " + tam + " lutador(es) sobrevivente(s): ";
                for(int i = 0; i<tam; i++){
                    texto = texto + fighter[winners[i]].get_name() + " com " + fighter[winners[i]].get_current_hp() + " de HP. ";
                }

                for(int m=0; m<total; m++) {
                    if(fighter[m].get_team() == fighter[winners[0]].get_team()) {
                        winrate[m]++;
                    }
                }
            }
        }

        // top damage
        int t1 = 0, t2 = 0, t3 = 0, id_t_1=20, id_t_2=20, id_t_3=20;
        for(int i=0; i<total; i++){
            if(damage[i] > t1){
                t3 = t2;
                id_t_3 = id_t_2;
                t2 = t1;
                id_t_2 = id_t_1;
                t1 = damage[i];
                id_t_1 = i;

            }else if(damage[i] > t2){
                t3 = t2;
                id_t_3 = id_t_2;
                t2 = damage[i];
                id_t_2 = i;
            }else if(damage[i] > t3){
                t3 = damage[i];
                id_t_3 = i;
            }
        }
        //atualizando se quebra records anteriores...
        if(t1 > top1) {
            top1 = t1;
            id_top1 = id_t_1;
            if(t2>top2){
                top2 = t2;
                id_top2 = id_t_2;
                if(t3>top3) {
                    top3 = t3;
                    id_top3 = id_t_3;
                }
            }else if(t2>top3){
                top3 = t2;
                id_top3= id_t_2;
            }
        }else if(t1 > top2){
            top2 = t1;
            id_top2 = id_t_1;
            if(t2>top3){
                top3 = t2;
                id_top3 = id_t_2;
            }
        }else if(t1 > top3){
            top3 = t1;
            id_top3 = id_t_1;
        }

        String texto2 = "\n TOP DANO CAUSADO: " + fighter[id_t_1].get_name() + " com " + t1 + ", "
                + fighter[id_t_2].get_name() + " com " + t2 + ", "
                + fighter[id_t_3].get_name() + " com " + t3 + ".";
        texto2 = texto2 + "\n\nHistórico de Especiais: " + especiais_historico + "\n";
        text_event.setText(texto + texto2);
        String temp = allfight;
        allfight = texto2 + "\n";
        allfight += temp;
        allfight += texto;
        save_game(winner);
    }

    public void score(View view){
        setContentView(R.layout.score);
        button_off();
        TextView text_event;
        text_event = findViewById(R.id.ato2);
        String texto = "";
        int winner = 0;
        for (int i = 0; i < total; i++) {
            if (!check_dead(i)) {
                winner++;
            }
        }
        int ord = ordem-1;
        if(mode == 1){
            texto = texto + "1º- ";
            for (int i=0; i<total ; i++){
                if(fighter[i].get_ordem() == -1){ // vencedor
                    if(fighter[i].get_team() == 1){
                        texto = texto + "O lutador escolhido " + fighter[i].get_name() + " é o vencedor! e matou " + fighter[i].get_kills() + " lutador(es)!\n";
                        break;
                    }else{
                        texto = texto + fighter[i].get_name() + " é o vencedor e matou " + fighter[i].get_kills() + " lutador(es)!\n";
                        break;
                    }
                }
            }
        }else if(mode == 2){
            for (int i=0; i<total ; i++){
                if(fighter[i].get_ordem() == -1){ // vencedores
                    texto = texto + "1º- ";
                    texto = texto + fighter[i].get_name() + " é um sobrevivente da equipe vencedora e matou " + fighter[i].get_kills() + " lutador(es)\n";
                }
            }
        }

        for (int i=0; i< total && ord>10; i++){
            if(fighter[i].get_ordem() == ord){
                ord --;
                texto = texto + (ordem-ord) + "º- " +fighter[i].get_name() + " matou " + fighter[i].get_kills() +
                        " lutador(es) e morreu para " + fighter[ fighter[i].get_id_kill() ].get_name() + "\n";
                i=-1;
            }
        }
        text_event.setText(texto);
    }

    public void score2(View view){
        setContentView(R.layout.score2);
        button_off();
        TextView text_event;
        text_event = findViewById(R.id.ato5);
        String texto = "";
        int ord = 10;
        for (int i=0; i< total && ord>0 ; i++){
            if(fighter[i].get_ordem() == ord){
                ord--;
                texto = texto + (ordem-ord) + "º- " + fighter[i].get_name() + " matou " + fighter[i].get_kills() +
                        " lutador(es) e morreu para " + fighter[ fighter[i].get_id_kill() ].get_name() + "\n";
                i=-1;
            }
        }
        text_event.setText(texto);
    }

    public void estati(View view){
        setContentView(R.layout.estati);
        button_off();
        TextView text_event;
        text_event = findViewById(R.id.ato3);
        String texto = "";
        int premios_escolhido = 0;

        if( mode == 1){
            boolean win = false;
            for (int i=0; i<total ; i++){
                if(!check_dead(i)) {
                    if(fighter[i].get_team() == 1){
                        premios_escolhido++;
                    }
                    win = true;
                    texto = texto + "Prêmio Vencedor e Sobrevivente: " + fighter[i].get_name() + " foi o lutador sobrevivente e vencedor\n\n";
                    break;
                }
            }
            if (!win)
                texto = texto + "Prêmio Vencedor e Sobrevivente: Não teve vencedor, todos os lutadores morreram\n\n";

            for (int i=0; i<total ; i++){
                if(fighter[i].get_ordem() == 1) { //encontrar a primeira morte ocorrida
                    if(fighter[ fighter[i].get_id_kill() ].get_team()  == -1) {
                        texto = texto + "Prêmio First Blood: O primeiro lutador foi morto por um evento da arena, portanto ninguem foi premiado.\n\n";
                    }else {
                        if(fighter[fighter[i].get_id_kill()].get_team() == 1){
                            premios_escolhido++;
                        }
                        texto = texto + "Prêmio First Blood:  " + fighter[fighter[i].get_id_kill()].get_name() + " foi o primeiro lutador a matar\n\n";
                    }
                    texto = texto + "Prêmio Aperitivo: " + fighter[i].get_name() + " foi o primeiro lutador a morrer.\n\n";
                    if(fighter[i].get_team() == 1){
                        premios_escolhido++;
                    }
                    break;
                }
            }

            int killer_win = -1;
            int top_kill = -1;
            for (int i=0; i<total ; i++){
                if(fighter[i].get_kills() > top_kill) {
                    killer_win = i;
                    top_kill = fighter[i].get_kills();
                }else if(fighter[i].get_kills() == top_kill){ // se houver empate, o que sobreviveu mais tempo ganha.
                    if(fighter[i].get_ordem() > fighter[killer_win].get_ordem() || fighter[i].get_ordem() == -1){
                        killer_win = i;
                    }
                }
            }
            if(fighter[killer_win].get_team() == 1){
                premios_escolhido++;
            }
            texto = texto + "Prêmio Serial Killer: " + fighter[killer_win].get_name() + " foi o lutador que mais matou com "
                    + top_kill + " lutador(es) morto(s)\n\n";


            int pass_win = -1;
            int less_kill = 100;
            for (int i=0; i<total ; i++){
                if(fighter[i].get_kills() < less_kill) {
                    pass_win = i;
                    less_kill = fighter[i].get_kills();
                }else if(fighter[i].get_kills() == less_kill){ // se houver empate, o que sobreviveu mais tempo ganha.
                    if(fighter[i].get_ordem() > fighter[pass_win].get_ordem()){
                        pass_win = i;
                    }
                }
            }
            if(fighter[pass_win].get_team() == 1){
                premios_escolhido++;
            }
            texto = texto + "Prêmio Pacifista: " + fighter[pass_win].get_name() +
                    " foi o lutador que menos matou tendo um total de " + less_kill + " lutador(es) morto(s)\n\n";
            texto = texto + "O lutador escolhido conquistou " + premios_escolhido + " prêmios\n";

        }
        else if(mode == 2){

            int equip_win = 0;
            for (int i = 0; i < total; i++) {
                if (!check_dead(i)) {
                    equip_win = fighter[i].get_team();
                    break;
                }
            }
            if (equip_win == 0) {
                texto = texto + "Prêmio Vencedor e Sobrevivente: Não teve equipe vencedora, todos os lutadores morreram\n\n";
            }else{
                texto = texto + "Prêmio Vencedor e Sobrevivente: A equipe " + equip_win + " foi a vencedora com o(s) lutador(es):\n\n";
                for (int i = 0; i < total; i++) {
                    if (!check_dead(i)) {
                        texto = texto + fighter[i].get_name() + ". ";
                    }else{
                        if(fighter[i].get_team() == equip_win)
                            texto = texto + fighter[i].get_name() + "(morto). ";
                    }
                }
            }
            if(equip_win == 1){
                premios_escolhido++;
            }
            texto = texto + "\n\n";
            //
            for (int i=0; i<total; i++) {
                if (fighter[i].get_ordem() == 1) {
                    texto = texto + "Prêmio Aperitivo: A equipe " + fighter[i].get_team() + " foi a primeira equipe que perdeu um lutador , sendo "
                            + fighter[i].get_name() + " o primeiro lutador morto.\n\n";
                    if(fighter[i].get_team() == 1){
                        premios_escolhido++;
                    }
                    if(fighter[ fighter[i].get_id_kill() ].get_team()  == -1){
                        texto = texto + "Prêmio First Blood: O primeiro lutador foi morto por um evento da arena, portanto ninguem foi premiado.\n\n";
                    }else {
                        texto = texto + "Prêmio First Blood: A equipe " + fighter[fighter[i].get_id_kill()].get_team() +
                                " foi a primeira equipe a eliminar um lutador, sendo " + fighter[fighter[i].get_id_kill()].get_name() + " o primeiro lutador a matar.\n\n";
                        if (fighter[fighter[i].get_id_kill()].get_team() == 1) {
                            premios_escolhido++;
                        }
                    }
                    break;
                }
            }
            //
            int t[] = new int[qtd_eq];
            int ord = ordem -1;
            for(int i=0; i<qtd_eq; i++){
                t[i]= -1;
            }
            t[0] = equip_win; //a equipe vencedora não pode ter sido a primeira a morrer portanto ja adicionada
            for (int i=0; i<total; i++) {
                if (fighter[i].get_ordem() == ord) {
                    for (int j = 0; j < qtd_eq; j++) {
                        if(fighter[i].get_team() == -1){
                            break;
                        }
                        if (fighter[i].get_team() == t[j]) { //ja foi adicionado
                            break;
                        } else {
                            if (t[j] == -1) { //tem vaga pra adicionar
                                t[j] = fighter[i].get_team();
                                break;
                            }
                        }
                    }
                    ord--;
                    if(t[qtd_eq-1] == -1){
                        i = -1;
                    }
                }
            }
            if (t[qtd_eq-1] == 1) {
                premios_escolhido++;
            }
            texto = texto + "Prêmio Equipe Aperitivo: A equipe " + t[qtd_eq-1] + " foi a primeira equipe a ter todos os lutadores mortos.\n\n";
            //
            int top_kill = 0, indice = 0;
            for(int i=0 ; i<qtd_eq; i++){
                t[i]= 0;
            }
            for (int i=0; i<total ; i++) {
                t[ fighter[i].get_team()-1] += fighter[i].get_kills();
            }
            for(int i=0; i<qtd_eq; i++){
                if (t[i] > top_kill) {
                    top_kill = t[i];
                    indice = i+1;
                }
            }
            if (fighter[indice].get_team() == 1) {
                premios_escolhido++;
            }
            texto = texto + "Prêmio Serial Killer: A equipe " + indice +
                    " foi a que mais matou, com " + top_kill + " lutador(es) morto(s) ao todo.\n\n";
            texto = texto + "A equipe escolhida conquistou " + premios_escolhido + " prêmios\n";
        }
        texto = texto + "(em caso de empate o prêmio é dado ao que sobreviveu maior tempo).";
        text_event.setText(texto);
    }

    public void dano(View view){

        String dano_txt = "   Classificação de Dano causado na Batalha:\n\n";
        int [] ordenado = new int[total];
        for(int i=0; i<total; i++) {
            ordenado[i] = -1;
        }
        int count = 0;
        for(int j= 0; j<total; j++){
            int maior = -1;
            int id_maior = -1;
            for(int i= 0; i<total; i++) {
                if (damage[i] > maior) {
                    boolean check = true;
                    for(int n=0; n<count+1 ; n++){
                        if(i == ordenado[n]){
                            check = false;
                        }
                    }
                    if(check){
                        maior = damage[i];
                        id_maior = i;
                    }
                }
            }
            dano_txt += (count+1) + "º- " + maior + " (" + fighter[id_maior].get_name() + ")\n";
            ordenado[count] = id_maior;
            count++;
        }
        click_sound();
        setContentView(R.layout.dano);
        button_off();
        TextView text = (TextView) findViewById(R.id.dano_text);
        text.setText(dano_txt);
    }

    public String random_event() {
        String texto = "";
        int r = rand.nextInt(100);
        int lut;
        if(r < 30) {
            lut = find_live_enemy(20);
            texto = texto + "Uma bola de fogo da arena atinge " + fighter[lut].get_name() +
                    " causando 30 de dano." + fight_damage(20, lut, 30);
        }else if (r < 50) {
            texto = texto + "A arena é subtamente cercada por uma magia de cura poderosa curando em 20 pontos todos os lutadores vivos.";
            for (int i=0; i<total; i++){
                fighter[i].healing(20);
            }
        }
        else if (r < 70) {
            texto = texto + "Toda a arena é coberta por uma chuva de flechas que pega todos os lutadores sem tempo de defesa, causando 10 de dano a todos.";
            texto = texto + area_attack(10, 20, 20);
        }
        else if (r < 80) {
            lut = find_live_enemy(20);
            fighter[lut].set_at_max(fighter[lut].get_at_max() + 10);
            fighter[lut].set_at_min(fighter[lut].get_at_min() + 10);
            texto = texto + "Uma arma mágica é lançada na arena e o lutador " + fighter[lut].get_name() + " faz uso dela ganhando 10 pontos de ataque máximo e mínimo permanentemente.";
        }else{
            lut = find_live_enemy(20);
            texto = texto + "Uma poção mágica é encontrada pelo lutador " + fighter[lut].get_name() + " que recupera 20 pontos de vida.";
            fighter[lut].healing(20);
        }
        return texto;
    }

    public  void all_text (View view){
        click_sound();
        setContentView(R.layout.all);
        button_off();
        TextView text_about = (TextView) findViewById(R.id.all_text);
        text_about.setText(allfight);
    }

    //select--------------------------------------------------------------------------------------------------------------------------------

    public void selected(View view){
        click_sound2();
        boolean keep_run = false;
        escolhas_restantes--;
        if(mode == 1){
            fighter[current_choose].set_team(1);
        }else{
            if(escolhas_restantes10 == 0){
                fighter[current_choose].set_team(10);
            }else if(escolhas_restantes9 == 0){
                fighter[current_choose].set_team(9);
            }else if(escolhas_restantes8 == 0){
                fighter[current_choose].set_team(8);
            }else if(escolhas_restantes7 == 0){
                fighter[current_choose].set_team(7);
            }else if(escolhas_restantes6 == 0){
                fighter[current_choose].set_team(6);
            }else if(escolhas_restantes5 == 0){
                fighter[current_choose].set_team(5);
            } else if(escolhas_restantes4 == 0 ){
                fighter[current_choose].set_team(4);
            }else if(escolhas_restantes3 == 0){
                fighter[current_choose].set_team(3);
            }else if(escolhas_restantes2 == 0){
                fighter[current_choose].set_team(2);
            }else{
                fighter[current_choose].set_team(1);
            }
        }
        if(escolhas_restantes>0)
            keep_run = true;
        else{
            if(eq == 10 && escolhas_restantes2>0)
                keep_run = true;
            else if(eq == 5 && escolhas_restantes4 > 0)
                keep_run = true;
            else if(eq == 4 && escolhas_restantes5 > 0)
                keep_run = true;
            else if(eq == 2 && escolhas_restantes10 > 0)
                keep_run = true;
        }

        if(keep_run){
            do{
                current_choose++;
                if(current_choose == total){ //voltar ao inicio
                    current_choose = 0;
                }
                if(current_choose < 0){ //voltar ao inicio
                    current_choose = total-1;
                }
            }
            while( fighter[current_choose].get_team() != -1);
        }

        select_show(null);
    }

    public void select_back(View view){
        click_sound();
        do{
            current_choose--;
            if(current_choose == total){ //voltar ao inicio
                current_choose = 0;
            }
            if(current_choose < 0){ //voltar ao inicio
                current_choose = total-1;
            }
        }
        while( fighter[current_choose].get_team() != -1);
        select_show(null);
    }

    public void select_next(View view){
        click_sound();
        do{
            current_choose++;
            if(current_choose == total){ //voltar ao inicio
                current_choose = 0;
            }
            if(current_choose < 0){ //voltar ao inicio
                current_choose = total-1;
            }
        }
        while( fighter[current_choose].get_team() != -1);
        select_show(null);
    }

    //status ----------------------------------------------------------------------------------------------------------------------------------

    public void states(View view) {
        remove_chamadas();
        click_sound();
        setContentView(R.layout.states);
        button_off();
        visualAutomatic();
        make_states(0);
        handler.postDelayed(runnable2, 2000);
        }

    public void states2 (View view){
        remove_chamadas();
        click_sound();
        setContentView(R.layout.states2);
        button_off();
        visualAutomatic();
        make_states(5);
        handler.postDelayed(runnable3, 2000);
    }

    public void states3 (View view){
        remove_chamadas();
        click_sound();
        setContentView(R.layout.states3);
        button_off();
        visualAutomatic();
        make_states(10);
        handler.postDelayed(runnable4, 2000);
    }

    public void states4 (View view){
        remove_chamadas();
        click_sound();
        setContentView(R.layout.states4);
        button_off();
        visualAutomatic();
        make_states(15);
        handler.postDelayed(btrunnable, 2000);
    }

    public void randomiz(View view){
        int r;
        if(escolhas_restantes2 > 0){
            //escolhendo time 1
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(1);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes2 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes2==0 && escolhas_restantes3 > 0){
            //escolhendo time 2
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(2);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes3 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes3==0 && escolhas_restantes4 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(3);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes4 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes4==0 && escolhas_restantes5 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(4);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes5 = 0;
            escolhas_restantes = eq;
        }if(escolhas_restantes5==0 && escolhas_restantes6 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(5);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes6 = 0;
            escolhas_restantes = eq;
        }if(escolhas_restantes6==0 && escolhas_restantes7 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(6);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes7 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes7==0 && escolhas_restantes8 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(7);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes8 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes8==0 && escolhas_restantes9 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(8);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes9 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes9==0 && escolhas_restantes10 > 0){
            //escolhendo time 3
            while(escolhas_restantes>0){
                r = rand.nextInt(20);
                if(fighter[r].get_team() == -1){
                    fighter[r].set_team(9);
                    escolhas_restantes--;
                }
            }
            escolhas_restantes10 = 0;
            escolhas_restantes = eq;
        }
        if(escolhas_restantes10<=0 && escolhas_restantes > 0){
            if(mode == 1){
                r = rand.nextInt(20);
                fighter[r].set_team(1);
                escolhas_restantes--;
            }else{
                //escolhendo time 4
                while(escolhas_restantes>0){
                    r = rand.nextInt(20);
                    if(fighter[r].get_team() == -1){
                        switch (eq){
                            case 2:
                                fighter[r].set_team(10);
                                break;
                            case 4:
                                fighter[r].set_team(5);
                                break;
                            case 5:
                                fighter[r].set_team(4);
                                break;
                            case 10:
                                fighter[r].set_team(2);
                        }

                        escolhas_restantes--;
                    }
                }
            }
        }
        select_show(null);
    }

    public void image_fighter(){
        ImageView img = (ImageView) findViewById(R.id.img_fgh);
        switch (current_choose){
            case 0:  //goblin
                img.setImageResource(R.drawable.goblin);
                break;
            case 1: //escudeiro
                img.setImageResource(R.drawable.shielder);
                break;
            case 2: //mago
                img.setImageResource(R.drawable.mage);
                break;
            case 3: //assassino
                img.setImageResource(R.drawable.assassin);
                break;
            case 4: //arquiera
                img.setImageResource(R.drawable.archer);
                break;
            case 5: //viking
                img.setImageResource(R.drawable.viking);
                break;
            case 6: //berserker
                img.setImageResource(R.drawable.berserker);
                break;
            case 7: //bruxa
                img.setImageResource(R.drawable.bruxa);
                break;
            case 8: //lanceiro
                img.setImageResource(R.drawable.lanceiro);
                break;
            case 9: //curandeira
                img.setImageResource(R.drawable.curandeira);
                break;
            case 10: //orc
                img.setImageResource(R.drawable.orc);
                break;
            case 11: //monge
                img.setImageResource(R.drawable.monge);
                break;
            case 12: //caçador
                img.setImageResource(R.drawable.hunter);
                break;
            case 13: //paladino
                img.setImageResource(R.drawable.palajin);
                break;
            case 14:// samurai
                img.setImageResource(R.drawable.samurai);
                break;
            case 15: //druida
                img.setImageResource(R.drawable.druida);
                break;
            case 16: //gladiador
                img.setImageResource(R.drawable.gladiador);
                break;
            case 17: //urso gigante
                img.setImageResource(R.drawable.bear);
                break;
            case 18: //ninja
                img.setImageResource(R.drawable.ninja);
                break;
            case 19: //lutador misterioso
                img.setImageResource(R.drawable.lutador_mist);
                break;
        }
    }

    public String especial_fighter(){
        String texto = "";
        switch (current_choose){
            case 0:  //goblin
                texto = "Utiliza um gás sonífero fazendo todos os lutadores dormirem até o fim da rodada, desferindo ataques aos inimigos.";
                break;
            case 1: //escudeiro
                texto = "Usa o escudo de forma a bloquear completamente os próximos dois ataques diretos ou revides seguintes (acumulativo).";
                break;
            case 2: //mago
                texto = "Conjura uma chuva de bolas de fogo causando 15 de dano a todos os lutadores (fogo amigo).";
                break;
            case 3: //assassino
                texto = "Executa um golpe mortal em um oponente, eliminando-o.";
                break;
            case 4: //arqueira
                texto = "Acerta uma flecha paralizante que impede o oponente de atacar por 2 rodadas.";
                break;
            case 5: //viking
                texto = "Recebe a benção de Odin sendo capaz de revidar todos os ataques por duas rodadas(acumulativo).";
                break;
            case 6: //berserker
                texto = "Se enfurece obtendo crítico (dobro do dano) nos seus próximos dois ataques(acumulativo).";
                break;
            case 7: //bruxa
                texto = "Absorve 25 pontos de vida do oponente e acrescenta aos seus.";
                break;
            case 8: //lanceiro
                texto = "Atravessa sua lança duas vezes atingindo oponentes, causando 20 de dano e aplcando a passiva em cada golpe.";
                break;
            case 9: //curandeira
                texto = "Conjura uma super cura para todos os seus ferimentos, recuperando todos os pontos de vida perdidos.";
                break;
            case 10: //orc
                texto = "Arremessa uma grande lamina que pode causar de 30 a 60 de dano a um oponente.";
                break;
            case 11: //monge
                texto = "Medita por um momento para aumentar permanentemente o poder de seu ataque máximo e mínimo em 10 pontos.(acumulativo).";
                break;
            case 12: //caçador
                texto = "Invoca um animal que irá atacar os oponentes por 3 rodadas, causando 10 a 20 de dano.";
                break;
            case 13: //paladino
                texto = "Realiza um encantamento que revigora suas forças, curando 15 pontos de vida e fortalecendo seus ataques permanentemente em 5 pontos(acumulativo).";
                break;
            case 14:// samurai
                texto = "Desfere um golpe que utiliza da força vital do oponente contra ele, caudando 7% (dobra a cada rodada, máximo 91%) da vida atual do inimigo como dano.";
                break;
            case 15: //druida
                texto = "Extrai energia da natureza ganhando 30 pontos de vida permanentemente (acumulativo).";
                break;
            case 16: //gladiador
                texto = "Realiza um grande golpe em área com sua maça que acerta 3 lutadores ao redor, causando 25 de dano a cada um (fogo amigo).";
                break;
            case 17: //urso gigante
                texto = "Abraça um oponente causando 50 de dano.";
                break;
            case 18: //ninja
                texto = "Se camufla nas sombras e não receberá ataques diretos por 2 rodadas(acumulativo).";
                break;
            case 19: //lutador misterioso
                texto = "???";
                break;
        }
        return texto;
    }

    public String passiva_fighter(){
        String texto = "";
        switch (current_choose){
            case 0:  //goblin
                texto = "Trapaceiro: Ataques envenenam inimigos causando(25% do ataque médio do alvo -2, mínimo 1) como dano adicional." +
                        " Coragem: Recebe 15 pontos de ataque máximo e mínimo ao restar 5 ou menos lutares vivos.";
            break;
            case 1: //escudeiro
                texto = "Bloqueo Frontal: Receberá no máximo 25 de dano de ataques diretos.";
                break;
            case 2: //mago
                texto = "Energizado: Ao atacar tem 15% de chance de atingir um lutador aleatório com um raio causando 20 de dano (fogo amigo).";
                break;
            case 3: //assassino
                texto = "Profissional: Executa inimigos que sobreviverem ao seu ataque com 5 de HP ou menos.";
                break;
            case 4: //arqueira
                texto = "Precisão: Quando possuir mais de 70 pontos de vida, recebe 10 pontos de ataque máximo e mínimo.";
                break;
            case 5: //viking
                texto = "Vingança: Cura 5 pontos de vida toda vez que revidar.";
                break;
            case 6: //berserker
                texto = "Adrenalina: Quando seu HP estiver abaixo de 40, recebe 15 pontos de ataque máximo e mínimo.";
                break;
            case 7: //bruxa
                texto = "Vampirismo: Seus ataques diretos absorvem vida, se curando (7% - 4 da vida máxima do alvo)." +
                        " Sangue Misturado: Inicia com 15 pontos perdidos de HP e perde 3 de HP ao final de cada rodada.";
                break;
            case 8: //lanceiro
                texto = "Lança afiada: A cada ataque ou revide realizado ganha 1 ponto de ataque máximo e mínimo (funciona com especial).";
                break;
            case 9: //curandeira
                texto = "Ressurreição: Ao sofrer dano mortal ressucita com 1 de HP. (Não sobrevive as execuções do assassino)";
                break;
            case 10: //orc
                texto = "Resistência: Não sofre dano de ataques em área.";
                break;
            case 11: //monge
                texto = "Inspiração: A cada inimigo que eliminar ganha 5 pontos de ataque máximo e mínimo. " +
                        "Ferimentos: ao fim de cada rodada seu ataque máximo e mínimo diminui em 1 ponto";
                break;
            case 12: //caçador
                texto = "Camuflagem: Tem 15% de chance de deixar de ser alvo de um inimigo.";
                break;
            case 13: //paladino
                texto = "Redirecionamento de Energia: Tem 15% de chance de cancelar o ataque e se curar com metade do dano que causaria.";
                break;
            case 14://samurai
                texto = "Corte agil Katana: Tem 5% de chance de atacar duas vezes o mesmo alvo. (Aumenta em 1% a cada rodada)";
                break;
            case 15: //druida
                texto = "Crescimento: Ao final de cada rodada recupera 2 de HP, aumentando também seu HP máximo.";
                break;
            case 16: //gladiador
                texto = "Ataques multi-alvo: Tem 20% de chance de causar o mesmo dano de ataque a outro lutador aleatório (fogo amigo).";
                break;
            case 17: //urso gigante
                texto = "Agressivo: Tem o dobro de chance de atacar algum inimigo, Cansaço: perde 2 pontos de ataque máximo cada vez que usar a passiva (mínimo 10).";
                break;
            case 18: //ninja
                texto = "Velocidade: Seus ataques tem 25% de chance a menos de sofrer revide.";
                break;
            case 19: //lutador misterioso
                texto = "Enfraquecimento: Seus ataques diretos enfraquecem o oponente reduzindo permanentemente 1 ponto no ataque máximo e mínimo." +
                        "(Lutadores com 10 de ataque ou menos não são afetados.)";
                break;
        }
        return texto;
    }

    //aux functions--------------------------------------------------------------------------------------------------------------------------

    public int find_live_enemy (int id_at){
        int r;
        if(mode == 1){
            do{
                r = rand.nextInt(total);
            }while (r == id_at || check_dead(r));
            return r;
        }else if (mode == 2){
            do{
                r = rand.nextInt(total);
            }while ( !is_enemy(id_at,r) || check_dead(r));
            return r;
        }else{
            return 0;
        }
    }

    public String fight_damage( int id_at, int id_def, int dmg){
        String texto = "";
        if(id_at != 20)
            damage[id_at] += dmg;
        int m = fighter[id_def].receive_dmg(dmg, id_at, ordem);
        if(id_at == 3 && fighter[id_def].get_current_hp() <= 5 && fighter[id_def].get_current_hp() > 0){ //assassino passiva
            texto = texto  + fighter[id_at].get_name() + " executou " + fighter[id_def].get_name() + " que estava gravemente ferido(P).";
            damage[id_at] += fighter[id_def].get_current_hp();
            fighter[id_def].receive_dmg( fighter[id_def].get_current_hp(), id_at , ordem);
            m = 1;
        }
        if(m == 2){
            texto = texto + "\n" + fighter[id_def].get_name() + " morreu pela primera vez e ressucitou com 1 de HP(P).";
        }
        if (m == 1) {
            texto = texto + "\n[" + fighter[id_at].get_name() + " matou " + fighter[id_def].get_name() + ".]";
            if(mode == 1)
                fighter[id_at].healing(10);

            if(id_at == 11){
                texto = texto + "\n" + fighter[id_at].get_name() + " aumentou seu ataque(P).";
                fighter[11].set_at_max( fighter[11].get_at_max() + 3);
                fighter[11].set_at_min( fighter[11].get_at_min() + 3);
            }
            if(fighter[id_def].get_team() == 1){
                texto = texto + "\nLutador escolhido foi morto!";
            }
            fighter[id_at].kills();
            ordem++;
            if(ordem == 15){
                fighter[0].passiva_gob();
            }
        }
        return texto;
    }

    public String area_attack(int dmg, int count, int id_at){
        int live = count_live();
        if ( (live -1) < count && id_at != 20){
            count = live -1;
        }
        if ( (live ) < count && id_at == 20){
            count = live;
        }

        String texto = "Atinge o(s) lutador(es): ";
        String texto2 = "";
        int[] sorted = new int[20];
        for (int j = 0; j<20; j++){
            sorted[j] = -1;
        }
        int id_def, aux=0;
        boolean check;
        for(int i = 0; i <count; i++){
            do{
                check = true;
                id_def = rand.nextInt(total);
                for (int j = 0; j<20; j++){
                    if(sorted[j] == id_def){
                        check = false;
                        break;
                    }
                }
            }while( check_dead(id_def) || (id_at == id_def) || !check);

            if(count == 1 || id_def != 10){
                if(aux <= 5){
                    texto = texto + fighter[id_def].get_name() + ". ";
                }
                if(id_at == 19){
                    if(fighter[id_def].get_at_max()>10)
                        fighter[id_def].set_at_max(fighter[id_def].get_at_max()-1);
                    if(fighter[id_def].get_at_min()>10)
                    fighter[id_def].set_at_min(fighter[id_def].get_at_min()-1);
                }
                texto2 = texto2 + fight_damage(id_at, id_def, dmg);
            }else{
                texto = texto + fighter[id_def].get_name() + " não recebe dano em área(P). ";
            }
            sorted[aux] = id_def;
            aux ++;
        }
            if(aux > 5){
                texto = texto + "(...)";
            }
            texto = texto + texto2;

        return texto;
    }

    public int count_live(){
        int live = 0;
        for (int i = 0; i < total; i++) {
            if (!check_dead(i)) {
                live++;
            }
        }
        return live;
    }

    public void visualAutomatic(){
        Button b = findViewById(R.id.auto);
        if (!automatic)
            b.setText("AUTO ON");
        else
            b.setText("AUTO OFF");
    }

    public void setAutomatic(View view) {
        if (automatic)
            automatic = false;
        else
            automatic = true;
        visualAutomatic();
    }

    public TextView name_find_id(int col){
        TextView text_event;
        switch (col){
            case 1:
                text_event = findViewById(R.id.ft1);
                break;
            case 2:
                text_event = findViewById(R.id.ft2);
                break;
            case 3:
                text_event = findViewById(R.id.ft3);
                break;
            case 4:
                text_event = findViewById(R.id.ft4);
                break;
            case 5:
                text_event = findViewById(R.id.ft5);
                break;
            default:
                text_event = findViewById(R.id.ft5);
        }
        return text_event;
    }

    public TextView hp_find_id(int col){
        TextView text_event;
        switch (col){
            case 1:
                text_event = findViewById(R.id.h1);
                break;
            case 2:
                text_event = findViewById(R.id.h2);
                break;
            case 3:
                text_event = findViewById(R.id.h3);
                break;
            case 4:
                text_event = findViewById(R.id.h4);
                break;
            case 5:
                text_event = findViewById(R.id.h5);
                break;
            default:
                text_event = findViewById(R.id.h5);
        }
        return text_event;
    }

    public TextView at_find_id(int col){
        TextView text_event;
        switch (col){
            case 1:
                text_event = findViewById(R.id.a1);
                break;
            case 2:
                text_event = findViewById(R.id.a2);
                break;
            case 3:
                text_event = findViewById(R.id.a3);
                break;
            case 4:
                text_event = findViewById(R.id.a4);
                break;
            case 5:
                text_event = findViewById(R.id.a5);
                break;
            default:
                text_event = findViewById(R.id.a5);
        }
        return text_event;
    }

    //save and load game ---------------------------------------------------------------------------------------------------------------

    public void load(){
        SharedPreferences prefs = getSharedPreferences("battle_save", Context.MODE_PRIVATE);
        if(mode == 1){
            try {
                JSONArray jsonArray1 = new JSONArray(prefs.getString("winrate", "[]"));
                for(int i=0; i<jsonArray1.length(); i++){
                    winrate[i] = jsonArray1.getInt(i);
                    if(winrate[i] > 0)
                        continue;
                    else
                        winrate[i] = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            n_games = prefs.getInt("games", 0);
        }else if (mode == 2){
            try {
                JSONArray jsonArray1 = new JSONArray(prefs.getString("winrate2", "[]"));
                for(int i=0; i<jsonArray1.length(); i++){
                    winrate[i] = jsonArray1.getInt(i);
                    if(winrate[i] > 0)
                        continue;
                    else
                        winrate[i] = 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            n_games = prefs.getInt("games2", 0);
        }
        top1 = prefs.getInt("top1", 0);
        top2 = prefs.getInt("top2", 0);
        top3 = prefs.getInt("top3", 0);
        id_top1 = prefs.getInt("id_top1", 20);
        id_top2 = prefs.getInt("id_top2", 20);
        id_top3 = prefs.getInt("id_top3", 20);

    }

    public void save_game(int win){

        SharedPreferences.Editor editor = getSharedPreferences("battle_save", MODE_PRIVATE).edit();
        JSONArray jsonArray2 = new JSONArray();

        for(int i=0; i<total; i++){
            jsonArray2.put(winrate[i]);
        }

        if(win != -1)
            n_games++;

        if(mode == 1){
            editor.putString("winrate", jsonArray2.toString());
            editor.putInt("games", n_games);
        }else if(mode == 2){
            editor.putString("winrate2", jsonArray2.toString());
            editor.putInt("games2", n_games);
        }

        editor.putInt("top1", top1);
        editor.putInt("top2", top2);
        editor.putInt("top3", top3);
        editor.putInt("id_top1", id_top1);
        editor.putInt("id_top2", id_top2);
        editor.putInt("id_top3", id_top3);

        editor.apply();
    }

    public void saved_edit(View view) {
        setContentView(R.layout.saved);
        EditText dft;
        dft = findViewById(R.id.l0);
        dft.setText( Integer.toString(winrate[0]) );
        dft = findViewById(R.id.l1);
        dft.setText(Integer.toString(winrate[1]));
        dft = findViewById(R.id.l2);
        dft.setText(Integer.toString(winrate[2]));
        dft = findViewById(R.id.l3);
        dft.setText(Integer.toString(winrate[3]));
        dft = findViewById(R.id.l4);
        dft.setText(Integer.toString(winrate[4]));
        dft = findViewById(R.id.l5);
        dft.setText(Integer.toString(winrate[5]));
        dft = findViewById(R.id.l6);
        dft.setText(Integer.toString(winrate[6]));
        dft = findViewById(R.id.l7);
        dft.setText(Integer.toString(winrate[7]));
        dft = findViewById(R.id.l8);
        dft.setText(Integer.toString(winrate[8]));
        dft = findViewById(R.id.l9);
        dft.setText(Integer.toString(winrate[9]));
        dft = findViewById(R.id.l10);
        dft.setText(Integer.toString(winrate[10]));
        dft = findViewById(R.id.l11);
        dft.setText(Integer.toString(winrate[11]));
        dft = findViewById(R.id.l12);
        dft.setText(Integer.toString(winrate[12]));
        dft = findViewById(R.id.l13);
        dft.setText(Integer.toString(winrate[13]));
        dft = findViewById(R.id.l14);
        dft.setText(Integer.toString(winrate[14]));
        dft = findViewById(R.id.l15);
        dft.setText(Integer.toString(winrate[15]));
        dft = findViewById(R.id.l16);
        dft.setText(Integer.toString(winrate[16]));
        dft = findViewById(R.id.l17);
        dft.setText(Integer.toString(winrate[17]));
        dft = findViewById(R.id.l18);
        dft.setText(Integer.toString(winrate[18]));
        dft = findViewById(R.id.l19);
        dft.setText(Integer.toString(winrate[19]));
        dft = findViewById(R.id.t1);
        dft.setText(Integer.toString(top1));
        dft = findViewById(R.id.t2);
        dft.setText(Integer.toString(top2));
        dft = findViewById(R.id.t3);
        dft.setText(Integer.toString(top3));
        dft = findViewById(R.id.id1);
        dft.setText(Integer.toString(id_top1));
        dft = findViewById(R.id.id2);
        dft.setText(Integer.toString(id_top2));
        dft = findViewById(R.id.id3);
        dft.setText(Integer.toString(id_top3));
    }

    public void saved_out(View view){
        int valor, total = 0;
        EditText input;
        input = findViewById(R.id.l0);
        valor = Integer.parseInt( input.getText().toString());
        winrate[0] = valor;
        input = findViewById(R.id.l1);
        valor = Integer.parseInt( input.getText().toString());
        winrate[1] = valor;
        input = findViewById(R.id.l2);
        valor = Integer.parseInt( input.getText().toString());
        winrate[2] = valor;
        input = findViewById(R.id.l3);
        valor = Integer.parseInt( input.getText().toString());
        winrate[3] = valor;
        input = findViewById(R.id.l4);
        valor = Integer.parseInt( input.getText().toString());
        winrate[4] = valor;
        input = findViewById(R.id.l5);
        valor = Integer.parseInt( input.getText().toString());
        winrate[5] = valor;
        input = findViewById(R.id.l6);
        valor = Integer.parseInt( input.getText().toString());
        winrate[6] = valor;
        input = findViewById(R.id.l7);
        valor = Integer.parseInt( input.getText().toString());
        winrate[7] = valor;
        input = findViewById(R.id.l8);
        valor = Integer.parseInt( input.getText().toString());
        winrate[8] = valor;
        input = findViewById(R.id.l9);
        valor = Integer.parseInt( input.getText().toString());
        winrate[9] = valor;
        input = findViewById(R.id.l10);
        valor = Integer.parseInt( input.getText().toString());
        winrate[10] = valor;
        input = findViewById(R.id.l11);
        valor = Integer.parseInt( input.getText().toString());
        winrate[11] = valor;
        input = findViewById(R.id.l12);
        valor = Integer.parseInt( input.getText().toString());
        winrate[12] = valor;
        input = findViewById(R.id.l13);
        valor = Integer.parseInt( input.getText().toString());
        winrate[13] = valor;
        input = findViewById(R.id.l14);
        valor = Integer.parseInt( input.getText().toString());
        winrate[14] = valor;
        input = findViewById(R.id.l15);
        valor = Integer.parseInt( input.getText().toString());
        winrate[15] = valor;
        input = findViewById(R.id.l16);
        valor = Integer.parseInt( input.getText().toString());
        winrate[16] = valor;
        input = findViewById(R.id.l17);
        valor = Integer.parseInt( input.getText().toString());
        winrate[17] = valor;
        input = findViewById(R.id.l18);
        valor = Integer.parseInt( input.getText().toString());
        winrate[18] = valor;
        input = findViewById(R.id.l19);
        valor = Integer.parseInt( input.getText().toString());
        winrate[19] = valor;
        input = findViewById(R.id.t1);
        valor = Integer.parseInt( input.getText().toString());
        top1 = valor;
        input = findViewById(R.id.t2);
        valor = Integer.parseInt( input.getText().toString());
        top2 = valor;
        input = findViewById(R.id.t3);
        valor = Integer.parseInt( input.getText().toString());
        top3 = valor;
        input = findViewById(R.id.id1);
        valor = Integer.parseInt( input.getText().toString());
        id_top1 = valor;
        input = findViewById(R.id.id2);
        valor = Integer.parseInt( input.getText().toString());
        id_top2 = valor;
        input = findViewById(R.id.id3);
        valor = Integer.parseInt( input.getText().toString());
        id_top3 = valor;
        if(mode == 1){
            for (int i=0; i<20; i++)
                total += winrate[i];
        }else if(mode == 2){
            for (int i=0; i<20; i++)
                total += winrate[i];
            if(total > 0)
                total = total/5;
        }
        n_games = total;
        select_show(null);
    }

    //funções específicas para ffa-----------------------------------------------------------------------------------------------------------


    public void coloring_ffa(int col, int j){
        TextView text_event;
        String text = ""+fighter[j].get_name();
        if(fighter[j].get_especial())
            text = text + "*";
        text_event = name_find_id(col);
        text_event.setText(text);
        text = ""+ fighter[j].get_current_hp();
        text_event = hp_find_id(col);
        text_event.setText(text);
        text = fighter[j].get_at_min() + "-" + fighter[j].get_at_max() ;
        text_event = at_find_id(col);
        text_event.setText(text);
        if(fighter[j].get_current_hp() == 0){
            text_event = name_find_id(col);
            text_event.setTextColor(Color.RED);
            text_event = hp_find_id(col);
            text_event.setTextColor(Color.RED);
            text_event = at_find_id(col);
            text_event.setTextColor(Color.RED);
        }else if (fighter[j].get_current_hp() < fighter[j].get_hp_max() ){
            if(fighter[j].get_current_hp() < 50){
                text_event = hp_find_id(col);
                text_event.setTextColor(Color.YELLOW);
            }else{
                text_event = hp_find_id(col);
                text_event.setTextColor(Color.GREEN);}
        }
        if(fighter[j].get_team() == 1){
            text_event = name_find_id(col);
            text_event.setTextColor(Color.CYAN);
        }
    }

    //funções específicas para luta em grupos -----------------------------------------------------------------------------------------------------

    public boolean is_enemy(int id_at, int id_def){
        if(fighter[id_at].get_team() == fighter[id_def].get_team()){
            return false;
        }else{
            return  true;
        }
    }

    public void coloring_be(int col, int j) {
        TextView text_event;
        String text = "" + fighter[j].get_name();
        if(fighter[j].get_especial())
            text = text + "*";
        text_event = name_find_id(col);
        text_event.setText(text);
        text = "" + fighter[j].get_current_hp();
        text_event = hp_find_id(col);
        text_event.setText(text);
        text = fighter[j].get_at_min() + "-" + fighter[j].get_at_max() ;
        text_event = at_find_id(col);
        text_event.setText(text);
        //color
        if (fighter[j].get_current_hp() < fighter[j].get_hp_max()  ) {
            if (fighter[j].get_current_hp() < 50) {
                text_event = hp_find_id(col);
                text_event.setTextColor(Color.YELLOW);
            } else {
                text_event = hp_find_id(col);
                text_event.setTextColor(Color.GREEN);
            }
        }
        if (fighter[j].get_current_hp() == 0) {
            text_event = name_find_id(col);
            text_event.setTextColor(Color.RED);
            text_event = hp_find_id(col);
            text_event.setTextColor(Color.RED);
            text_event = at_find_id(col);
            text_event.setTextColor(Color.RED);
        }else {
            //sem sistema de cores por enquanto
        }
    }

    public boolean not_single_equip(){
        int equip = 0;
        for (int i = 0; i < total; i++) {
            if (!check_dead(i)) {
                if(equip == 0){
                    equip = fighter[i].get_team();
                }else if (equip != fighter[i].get_team()){
                    return true;
                }
            }
        }
        return false;
    }

    //checks----------------------------------------------------------------------------------------------------------------------------------------

    public boolean check_viking(int id){
        if(viking > 0 && id == 5){
            return  true;
        }else{
            return  false;
        }
    }

    public boolean check_ninja(int id){
        if(id == 18 && ninja>0) {
            return true;
        }
        return false;
    }

    public boolean check_arqueiro(int id){
        if(arqueiro>0 && fighter[id].get_no_attack()) {
                return true;
        }
        return false;
    }

    public boolean check_escudeiro(int id){
        if(id == 1 && escudeiro > 0){
            escudeiro--;
            if(escudeiro == 0)
                fighter[1].set_especial(false);
            return true;
        }else{
            return false;
        }
    }

    public boolean check_berserker(int id){
        if(id == 6 && berserker>0) {
            berserker--;
            if(berserker == 0)
                fighter[6].set_especial(false);
            return true;
        }
        return false;
    }

    public boolean check_dead( int id){
        if ( fighter[id].get_current_hp() == 0){
            return true;
        }else{
            return false;
        }
    }

    //exit-----------------------------------------------------------------------------------------------------------------------------------------

    public void exit(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public  void menu_return (View view){
        click_sound();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", enable_song);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    //music---------------------------------------------------------------------------------------------------------------------------------------

    public  void play_song_ambient(View view){
        if(view !=null ){
            set_enable_song();
        }
        if(music == null){
            music = MediaPlayer.create(this, R.raw.ambient); //por enquanto é menu na musica
            music.setLooping(true);
            music.setVolume(0.5f, 0.5f);
            play_song_fun();
        }else{
            stop_song();
        }
    }

    private void play_song_fun() {
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop_song();
            }
        });
        music.start();
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

    public void click_sound2(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.click2);
            mp.setVolume(0.3f, 0.3f);
            mp.start();
        }
    }

    private void button_off(){
        if(!enable_song) {
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        }else{
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }

    @Override

    protected void onResume(){
        super.onResume();
        if(enable_song==true){
            play_song_ambient(null);
        }
    }

    protected void onRestart() {
        super.onRestart();
        enable_song = false;
        if (enable_song == true) {
            play_song_ambient(null);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        stop_song();
    }
}

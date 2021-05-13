package com.Nivestar.battle;

import java.util.Random;

public class Fighter {

    private int id;
    private int hp_max;
    private int current_hp;
    private int at_max;
    private int at_min;
    private String name;
    private int team;
    private int kills;
    private int ordem;
    private int id_killer;
    private String special;
    private boolean no_attack;
    private boolean especial;
    private String cls;
    private String passiva;
    private String counter;
    private int counter_id;
    private boolean curandeira_passiva;
    private boolean goblin_passiva;

    static Random rand = new Random();
    

    public Fighter(int id_choose, int team) {

        switch (id_choose) {  //random enemy rank dificulty with based in player nivel
            case 0:
                this.name = "Goblin";
                this.special = "A";
                this.hp_max = 100;
                this.at_max = 25;
                this.at_min = 1;
                this.cls = "OTHER";
                this.passiva = "B";
                this.counter = "Counter: Monge\nFraqueza: Curandeira";
                this.counter_id = 11;
                this.goblin_passiva = false;
                break;
            case 1:
                this.name = "Escudeiro";
                this.special = "A";
                this.hp_max = 140;
                this.at_max = 9;
                this.at_min = 5;
                this.cls = "TANK";
                this.passiva = "A";
                this.counter = "Counter: Arqueira\nFraqueza: Samurai";
                this.counter_id = 4;
                break;
            case 2:
                this.name = "Mago";
                this.special = "A";
                this.hp_max = 100;
                this.at_max = 50;
                this.at_min = 0;
                this.cls = "DPS";
                this.passiva = "C";
                this.counter = "Counter: Ninja\nFraqueza: Orc";
                this.counter_id = 18;
                break;
            case 3:
                this.name = "Assassino";
                this.special = "A";
                this.hp_max = 100;
                this.at_max = 41;
                this.at_min = 13;
                this.cls = "DPS";
                this.passiva = "A";
                this.counter = "Counter: Curandeira\nFraqueza: Berserker";
                this.counter_id = 9;
                break;
            case 4:
                this.name = "Arqueira";
                this.special = "C";
                this.hp_max = 100;
                this.at_max = 38;
                this.at_min = 22;
                this.cls = "DPS";
                this.passiva = "B";
                this.counter = "Counter: Lanceiro\nFraqueza: Escudeiro";
                this.counter_id = 8;
                break;
            case 5:
                this.name = "Viking";
                this.special = "C";
                this.hp_max = 130;
                this.at_max = 27;
                this.at_min = 13;
                this.cls = "BRUISER";
                this.passiva = "B";
                this.counter = "Counter: Orc\nFraqueza: Lanceiro";
                this.counter_id = 10;
                break;
            case 6:
                this.name = "Berserker";
                this.special = "B";
                this.hp_max = 110;
                this.at_max = 33;
                this.at_min = 13;
                this.cls = "OTHER";
                this.passiva = "B";
                this.counter = "Counter: Assassino\nFraqueza: Ninja";
                this.counter_id = 3;
                break;
            case 7:
                this.name = "Bruxa";
                this.special = "A";
                this.hp_max = 140;
                this.at_max = 26;
                this.at_min = 20;
                this.cls = "OTHER";
                this.passiva = "B";
                this.counter = "Counter: Druida\nFraqueza: Paladino";
                this.counter_id = 15;
                break;
            case 8:
                this.name = "Lanceiro";
                this.special = "B";
                this.hp_max = 110;
                this.at_max = 30;
                this.at_min = 20;
                this.cls = "DPS";
                this.passiva = "A";
                this.counter = "Counter: Viking\nFraqueza: Arqueira";
                this.counter_id = 5;
                break;
            case 9:
                this.name = "Curandeira";
                this.special = "?";
                this.hp_max = 120;
                this.at_max = 18;
                this.at_min = 14;
                this.cls = "OTHER";
                this.passiva = "A";
                this.counter = "Counter: Goblin\nFraqueza: Assassino";
                this.counter_id = 0;
                this.curandeira_passiva = true;
                break;
            case 10:
                this.name = "Orc";
                this.special = "B";
                this.hp_max = 140;
                this.at_max = 19;
                this.at_min = 7;
                this.cls = "TANK";
                this.passiva = "C";
                this.counter = "Counter: Mago\nFraqueza: Viking";
                this.counter_id = 2;
                break;
            case 11:
                this.name = "Monge";
                this.special = "B";
                this.hp_max = 120;
                this.at_max = 31;
                this.at_min = 21;
                this.cls = "BRUISER";
                this.passiva = "B";
                this.counter = "Counter: Gladiador\nFraqueza: Goblin";
                this.counter_id = 16;
                break;
            case 12:
                this.name = "Caçador";
                this.special = "A";
                this.hp_max = 105;
                this.at_max = 35;
                this.at_min = 27;
                this.cls = "OTHER";
                this.passiva = "A";
                this.counter = "Counter: Urso\nFraqueza: Druida";
                this.counter_id = 17;
                break;
            case 13:
                this.name = "Paladino";
                this.special = "B";
                this.hp_max = 125;
                this.at_max = 25;
                this.at_min = 14;
                this.cls = "BRUISER";
                this.passiva = "B";
                this.counter = "Counter: Bruxa\nFraqueza: Lutador Misterioso";
                this.counter_id = 7;
                break;
            case 14:
                this.name = "Samurai";
                this.special = "A-C";
                this.hp_max = 115;
                this.at_max = 25;
                this.at_min = 25;
                this.cls = "BRUISER";
                this.passiva = "A-C";
                this.counter = "Counter: Escudeiro\nFraqueza: Urso";
                this.counter_id = 1;
                break;
            case 15:
                this.name = "Druida";
                this.special = "A";
                this.hp_max = 130;
                this.at_max = 17;
                this.at_min = 13;
                this.cls = "TANK";
                this.passiva = "A";
                this.counter = "Counter: Caçador\nFraqueza: Bruxa";
                this.counter_id = 12;
                break;
            case 16:
                this.name = "Gladiador";
                this.special = "C";
                this.hp_max = 145;
                this.at_max = 15;
                this.at_min = 10;
                this.cls = "TANK";
                this.passiva = "C";
                this.counter = "Counter: Lutador Misterioso\nFraqueza: Monge";
                this.counter_id = 19;
                break;
            case 17:
                this.name = "Urso Gigante";
                this.special = "B";
                this.hp_max = 150;
                this.at_max = 22;
                this.at_min = 9;
                this.cls = "TANK";
                this.passiva = "C";
                this.counter = "Counter: Samurai\nFraqueza: Caçador";
                this.counter_id = 14;
                break;
            case 18:
                this.name = "Ninja";
                this.special = "A";
                this.hp_max = 100;
                this.at_max = 30;
                this.at_min = 28;
                this.cls = "DPS";
                this.passiva = "B";
                this.counter = "Counter: Berserker\nFraqueza: Mago";
                this.counter_id = 6;
                break;
            case 19:
                this.name = "Lutador Misterioso";
                this.special = "B";
                this.hp_max = rand.nextInt(140 - 110 + 1) + 110;
                this.at_max = rand.nextInt(35 - 20 + 1) + 20;
                this.at_min = rand.nextInt(15 - 10 + 1) + 10;
                this.cls = "BRUISER";
                this.passiva = "B";
                this.counter = "Counter: Paladino\nFraqueza: Gladiador";
                this.counter_id = 13;
                break;

            case 20:
                this.name = "Arena";
                this.current_hp = -1;
            default:
                System.err.println("ERRo! invalid fighter");
        }
        this.id = id_choose;
        if(id_choose == 7){
            this.current_hp = 125;
        }else{
            this.current_hp = this.hp_max;
        }
        this.team = team;
        this.kills = 0;
        this.ordem = -1;
        this.id_killer = -1;
        this.no_attack = false;
        this.especial = false;
    }

    public void classificar(int mode){
        if(mode == 1){
            if(this.team == 1){
                this.name = this.name + " [E]";
            }
        }else {
            switch (this.team) {
                case 1:
                    this.name = this.name + " [1]";
                    break;
                case 2:
                    this.name = this.name + " [2]";
                    break;
                case 3:
                    this.name = this.name + " [3]";
                    break;
                case 4:
                    this.name = this.name + " [4]";
                    break;
                case 5:
                    this.name = this.name + " [5]";
                    break;
                case 6:
                    this.name = this.name + " [6]";
                    break;
                case 7:
                    this.name = this.name + " [7]";
                    break;
                case 8:
                    this.name = this.name + " [8]";
                    break;
                case 9:
                    this.name = this.name + " [9]";
                    break;
                case 10:
                    this.name = this.name + " [10]";
                    break;
            }
        }
        if(this.id == 4){//passiva arqueira
            this.name = this.name + "(P)";
            this.at_min += 10;
            this.at_max += 10;
        }
    }

    public int attack() {
        return (rand.nextInt(this.at_max - this.at_min + 1) + this.at_min);
    }

    public int make_dead(int ordem, int id_at) {
        if (this.current_hp <= 0) {
            if(this.id == 9 && id_at != 3){
                if(this.curandeira_passiva){ //passiva curandeira
                    this.name = this.name + "(P)";
                    this.current_hp = 1;
                    this.curandeira_passiva = false;
                    return 2;
                }
            }
            this.current_hp = 0;
            this.ordem = ordem;
            this.id_killer = id_at;
            if(this.no_attack){ //retirando no ataque
                this.no_attack = false;
                this.name = this.name.replace("(X)","");
            }
            if(this.id == 9){
                this.name = this.name.replace("(P)","");
            }
            if(this.id == 0){
                this.name = this.name.replace("(P)","");
            }
            return 1;
        } else {
            return 0;
        }
    }

    public void passiva_gob(){
        this.goblin_passiva = true;
        this.name = this.name + "(P)";
        this.at_max = this.at_max + 15;
        this.at_min = this.at_min + 15;
    }

    public void kills(){
        this.kills += 1;
    }

    public int receive_dmg(int dmg, int id_at, int ordem){
        boolean check_arq = true, check_ber = true;
        if(this.current_hp <= 70){
            check_arq = false;
        }
        if(this.current_hp < 40){
            check_ber = false;
        }
        this.current_hp = this.current_hp - dmg;
        if(this.id == 4 && this.current_hp <= 70 && check_arq){//passiva arqueira
            this.name = this.name.replace("(P)","");
            this.at_min -= 10;
            this.at_max -= 10;
        }
        if(this.id == 6 && this.current_hp < 40 && check_ber){//passiva bersek
            this.name = this.name + "(P)";
            this.at_min += 15;
            this.at_max += 15;
        }
        int d = make_dead(ordem, id_at);
        if(d == 1 && this.id == 6) {
            this.name = this.name.replace("(P)","");
        }
        return d;
    }

    public void healing(int heal) {
        if(this.current_hp != 0) {
            boolean check_arq = true, check_ber = true;
            if(this.current_hp > 70){
                check_arq = false;
            }
            if(this.current_hp >= 40){
                check_ber = false;
            }
            int h = this.current_hp + heal;
            if (h > this.hp_max) {
                this.current_hp = this.hp_max;
            } else {
                this.current_hp = h;
            }
            if(this.id == 4 && this.current_hp > 70 && check_arq){//passiva arqueira
                this.name = this.name + "(P)";
                this.at_min += 10;
                this.at_max += 10;
            }
            if(this.id == 6 && this.current_hp >= 40 && check_ber){//passiva berserk
                this.name = this.name.replace("(P)","");
                this.at_min -= 15;
                this.at_max -= 15;
            }
        }
    }

    //g and s

    public String get_name() {
        return this.name;
    }

    public int get_hp_max() {
        return this.hp_max;
    }

    public int get_current_hp() {
        return this.current_hp;
    }

    public int get_team() {
        return this.team;
    }

    public int get_kills() {
        return this.kills;
    }

    public int get_ordem() {
        return this.ordem;
    }

    public int get_id_kill() {
        return this.id_killer;
    }

    public void set_at_max(int x) {
        this.at_max = x;
    }

    public int get_at_max() {
        return this.at_max;
    }

    public void set_at_min(int x) {
        this.at_min = x;
    }

    public int get_at_min() {
        return this.at_min;
    }

    public String get_special() {
        return this.special;
    }

    public void set_hp_max(int x) {
        this.hp_max = x;
    }

    public void set_no_attack(boolean x){
        this.no_attack = x;
        if(x){
            this.name = this.name + "(X)";
        }else{
            this.name = this.name.replace("(X)","");
        }
    }

    public boolean get_no_attack(){
        return this.no_attack;
    }

    public void set_especial(boolean s){
        this.especial = s;
    }

    public boolean get_especial(){
        return this.especial;
    }

    public void set_team(int team) {
        this.team = team;
    }

    public String get_cls (){
        return  this.cls;
    }

    public String get_passiva(){
        return this.passiva;
    }

    public String get_counters(){
        return this.counter;
    }

    public int get_counterID(){
        return this.counter_id;
    }
}
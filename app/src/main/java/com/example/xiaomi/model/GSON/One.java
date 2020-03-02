package com.example.xiaomi.model.GSON;

public class One {

    /*
    返回的json数据:
    {
        "id": 639,
            "hitokoto": "没问题，绝对没问题",
            "type": "a",
            "from": "木之本樱（无敌咒语）",
            "creator": "树形图设计者",
            "created_at": "1475079679"
    }*/
    private  int id;
    private  String hitokoto;
    private  String from ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

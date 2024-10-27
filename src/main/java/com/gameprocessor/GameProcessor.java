package com.gameprocessor;

import com.gameprocessor.dispatcher.Dispatcher;
import com.gameprocessor.dispatcher.filters.And;
import com.gameprocessor.dispatcher.filters.Command;
import com.gameprocessor.dispatcher.filters.Or;
import com.gameprocessor.dispatcher.filters.State;
import com.gameprocessor.dispatcher.handlers.Handler;
import com.gameprocessor.entities.creatures.Creature;
import com.gameprocessor.entities.creatures.Dwarf;
import com.gameprocessor.entities.creatures.Human;
import com.gameprocessor.entities.items.*;
import com.gameprocessor.resourcemanager.Resource;
import com.gameprocessor.resourcemanager.ResourceManager;
import com.gameprocessor.entities.*;
import com.gameprocessor.user.Request;
import com.gameprocessor.user.Response;
import com.gameprocessor.user.UserData;

import java.util.LinkedList;
import java.util.List;

/**
 * Неактуально TODO
 * Класс, реализующий игровые механики.
 * Имеет в себе 2 типа публичных функций.
 * 1. публичная функция без аргументов:
 *      является игровой коммандой, которую
 *      может вызвать пользователь
 * 2. публичная функция принимающая в качестве
 * аргумента объект реализующий Sendable:
 *      является реализацией действия,
 *      которой совершается с этим объектом
 */
public class GameProcessor {
    public static final String NONE = "";
    public static final String COMBAT = "COMBAT";
    public static final String CREATE = "CREATE";
    public static final Creature[] RACES = new Creature[]{new Dwarf(), new Human()};

    /**
     * Отправляет текстовое сообщение пользователю
     * @param request запрос, на который готовится response
     * @param text текст, для пользователя
     */
    private static void send(Request request, String text){
        request.response = Response.builder()
                .userId(request.getUserId())
                .text(text)
                .build();
    }

    /**
     * Отправляет объекты пользователю, с текстом из getName
     * @param request запрос, на который готовится response
     * @param text текст, для пользователя
     * @param resources объекты для отправки, должны быть Sendable
     */
    private void send(Request request, String text, List<Resource> resources){
        var response = Response.builder()
                .userId(request.getUserId())
                .text(text);
        for(Resource resource: resources){
            if(resource.get() instanceof Sendable obj) {
                response.addObject(obj.getName(), resource.getId());
            }
        }
        request.response = response.build();
    }

    /**
     * Отправляет действия объекта пользователю
     * @param request запрос, на который готовится response
     * @param resource объект, чьи действия отправляются, должен быть Sendable
     */
    private void send(Request request, Resource resource){
        if(resource.get() instanceof Sendable obj){
            var response = Response.builder()
                    .userId(request.getUserId())
                    .text(obj.getDescription());
            for(String action: obj.getActions().getActions()){
                response.addObject(action, resource.getId()+":"+action);
            }
            request.response = response.build();
        }
    }

    public Response handleRequest(Request r) {
        Dispatcher dp = GameDispatcher.get();
        dp.addHandler(
                new Handler(
                        new Command("start"),
                        request -> {
                            start(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new Command("data"),
                        request -> {
                            data(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new Or(
                                new Command("retry"),
                                new Command("Y")
                        ),
                        request -> {
                            retry(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new Command("help"),
                        request -> {
                            help(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new Command("N"),
                        request -> {
                            request.response = Response.builder().userId(request.getUserId()).text("Действие отменено").build();
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new And(
                                new State(COMBAT),
                                new Command("await")
                        ),
                        request -> {
                            await(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new And(
                                new State(COMBAT),
                                new Command("attack")
                        ),
                        request -> {
                            attack(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new And(
                                new State(NONE),
                                new Command("inspect")
                        ),
                        request -> {
                            inspect(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        new And(
                                new State(NONE),
                                new Command("bag")
                        ),
                        request -> {
                            bag(request);
                        }
                )
        );
        dp.addHandler(
                new Handler(
                        request -> true,
                        request -> {
                            try {
                                    String[] args = request.getCallbackData().split(":");
                                    if(args.length < 3) return;
                                    Resource resource = new Resource(args[0] + ":" + args[1] + ":" + args[2]);
                                    if (resource.get() instanceof Creature creature) {
                                        attack(request, resource);
                                        return;
                                    }
                                    if (args.length < 4) {
                                        send(request, resource);
                                        return;
                                    }
                                    switch (args[3]) {
                                        case "loot" -> loot(request, resource);
                                        case "equip" -> equip(request, resource);
                                        case "unequip" -> unequip(request, resource);
                                        case "open" -> open(request, resource);
                                        case "use" -> use(request,resource);
                                        case "raiseHp" -> raiseHp(request);
                                        case "raiseAp" -> raiseAp(request);
                                        default -> request.response = Response.builder().build();
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                request.response = Response.builder().build();
                            }
                        }
                )
        );
        dp.call(r);
        return r.response;
    }
    private Room generateFloor(int numberOfRooms, List<List<Item>> items, List<List<Creature>> enemies, Request request) {
        List<Room.RoomBuilder> rooms = new LinkedList<>();
        List<List<Item>> randomItems= new LinkedList<>();
        List<List<Creature>> randomEnemies=new LinkedList<>();
        List<List<Integer>>doorsId=new LinkedList<>();
        while(!items.isEmpty())
        {
            int randomNumber=((int)(Math.random()*(items.size())));
            randomItems.add(items.get(randomNumber));
            items.remove(randomNumber);
        }
        while(!enemies.isEmpty())
        {
            int randomNumber=((int)(Math.random()*(enemies.size())));
            randomEnemies.add(enemies.get(randomNumber));
            enemies.remove(randomNumber);
        }

        doorsId.add(new LinkedList<>());
        for (int i=1;i<numberOfRooms;i++)
        {
            doorsId.add(new LinkedList<>());
            int randomRoom=((int)(Math.random()*i));
            doorsId.get(i).add(randomRoom);
            //doorsId.get(randomRoom).add(i);
        }

        for(int i=0;i<numberOfRooms;i++)
        {
            rooms.add(
                    Room.builder("Room "+(i+1))
                    .userId(request.getUserId())
                    .addItems(randomItems.get(i))
                    .addEnemies(randomEnemies.get(i))
            );
        }

        int k = 1;
        for(int i = numberOfRooms-1; i >= 0; i--) {
            Room room = rooms.get(i).build();
            for(int j: doorsId.get(i)) {
                rooms.get(j).addItem(
                        new Door("Door " + k++, "Door to " + room.getName(), room, request.getUserId())
                );
            }
        }

        return rooms.get(0).build();
    }

    private void createUser(Request request){
        List<List<Item>>items=new LinkedList<>();
        List<List<Creature>>enemies=new LinkedList<>();

        for(int i=0;i<5;i++)
        {
            items.add(new LinkedList<>());
            enemies.add(new LinkedList<>());

        }
        items.get(0).add( Box.builder("Chest", "Old chest")
                .userId(request.getUserId())
                .addItem(new Equipment("Weapon", "Bad weapon", 0, 2))
                .build());
        items.get(1).add( Box.builder("Chest", "Old chest")
                .userId(request.getUserId())
                .addItem(new Equipment("Шлем", "Металлический шлем", 5, 1))
                .build());
        items.get(2).add(Box.builder("Chest", "Old chest")
                .userId(request.getUserId())
                .addItem(new Equipment("armor", "Iron armor", 10, 1))
                .build());
        enemies.get(0).add(new Creature("bat",3,1, 5));
        enemies.get(1).add((new Creature("spider", 3, 1, 5)));
        enemies.get(2).add(new Creature("slime",5,1, 5));

        Room room=generateFloor(5,items,enemies,request);

        Resource resource = ResourceManager.createResource(
                request.getUserId(),
                "UserData",
                new UserData(
                        request.getUserId(),
                        new Creature("player",10,1,0),
                        room,
                        new LevelManager()
                )
        );
    }

    private void createUserForShow(Request request) {
        var room = Room.builder("Room 1").userId(request.getUserId())
                .addItem(
                        Box.builder("Chest", "Old chest")
                                .userId(request.getUserId())
                                .addItem(new Consumable("Hp potion", "Regen 5 hp", 5))
                                .addItem(new Equipment("Weapon", "Bad weapon", 0, 2))
                                .build()
                )
                .addItem(
                        Box.builder("Some Chest", "Chest")
                                .userId(request.getUserId())
                                .addItem(new Equipment("Шлем", "Металлический шлем", 5, 1))
                                .build()
                )
                .addItem(
                        new Door("Door",
                                "Door to room 2",
                                Room.builder("Room 2")
                                .userId(request.getUserId())
                                .addItem(
                                        new Door("Door",
                                                "Door to room 3",
                                                Room.builder("Room 3")
                                                        .userId(request.getUserId())
                                                        .addEnemy(new Creature("slime",5,1, 5))
                                                        .build(),
                                                request.getUserId()
                                        )
                                )
                                .addEnemy(new Creature("bat",3,1, 5))
                                .addEnemy(new Creature("spider", 3, 1, 5))
                                .addItem(
                                        Box.builder("Chest", "Chest")
                                                .userId(request.getUserId())
                                                .addItem(new Consumable("Hp potion", "Regen 5 hp", 5))
                                                .addItem(new Equipment("Boots", "Just boots", 1, 0))
                                                .build()
                                )
                                .build(),
                                request.getUserId()
                        )
                )
                .build();

        Resource resource = ResourceManager.createResource(
                request.getUserId(),
                "UserData",
                new UserData(
                        request.getUserId(),
                        new Creature("player",10,1,0),
                        room,
                        new LevelManager()
                )
        );
    }

    private void start(Request request) {
        if(ResourceManager.hasUser(request.getUserId())) {
            var response = Response.builder()
                    .userId(request.getUserId())
                    .text("Игра запущена. Хотите начать заново?");
            response.addObject("Да", "/retry");
            response.addObject("Нет", "/N");
            request.response = response.build();
            return;
        }
        send(request,
                """
                Добро пожаловать в нашу текстовую РПГ
                Ваша задача: выбраться из подземелья
                """);
        create(request);
    }

    private void create(Request request) {
        if(ResourceManager.hasUser(request.getUserId())) {
            start(request);
            return;
        }
        createUserForShow(request);
        UserData userData = (UserData) request.getUserData().get();
        userData.state = CREATE;
        request.getUserData().update(userData);
        var response = Response.builder().userId(request.getUserId());
        StringBuilder text = new StringBuilder(request.response.text).append("\nВыберете расу:");
        for(Creature i: RACES) {
            text.append("\n").append(i.getInfo());
            response.addObject(i.getClass().getSimpleName(), i.getClass().getSimpleName());
        }
        request.response = response.text(text.toString()).build();
    }

    private void inspect(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Room room = (Room) userData.getRoom().get();
        send(request,"Вы находитесь в " + room.getName() + ":", room.getItems());
    }

    private void data(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        LevelManager levelManager =  (LevelManager) userData.getLevelManager().get();
        send(request,"Name: " + player.getName() +
                "\nРаса: " + player.getClass().getSimpleName() +
                "\nHp: " + player.getHp()  + "/" + player.getMaxHp() +
                "\nAp: " + player.getAp() +
                "\nExp: " + levelManager.getExp() +
                "\nLevel: " + levelManager.getLevel() +
                "\nNext level: " + levelManager.getNextLevel() +
                "\nЭкипировано:", player.getEquipment()
        );
    }

    private void help(Request request){
        send(request,
                """
                    Ваша задача: выбраться из подземелья
                    /inspect - осмотреть окружение
                    /data - посмотреть информацию о персонаже
                    /bag - открыть инвентарь
                    /retry - начать заново
                    /help - помощь
                    """);
    }

    private void retry(Request request) {
        ResourceManager.deleteUser(request.userId);
        start(request);
    }

    private void bag(Request request){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        send(request,"В вашем инвентаре:", player.getInventory());
    }

    private void take(Item item){
        //TODO подбирать предметы из комнаты
    }

    private void loot(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Box box = (Box) resource.get();
        StringBuilder builder = new StringBuilder("Вы получили:\n");
        for(Resource i: box.getItems()){
            Item item = (Item) i.get();
            builder.append(item.getName());
            builder.append("\n");
            player.getInventory().add(i);
        }
        resource.update(Box.builder(box.getName(), "Пусто").build());
        userData.getPlayer().update(player);
        send(request, builder.toString());
    }

    private void equip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        if(equipment.equip(player, resource)) {
            player.getInventory().remove(resource);
            send(request, "Вы надели " + equipment.getName());
            userData.getPlayer().update(player);
        }
        else send(request, "У вас не получилось надеть " + equipment.getName());
        resource.update(equipment);
    }

    private void unequip(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Equipment equipment = (Equipment) resource.get();
        if(equipment.unequip(player, resource)) {
            player.getInventory().add(resource);
            send(request, "Вы сняли " + equipment.getName() + " и положили в инвентарь");
            userData.getPlayer().update(player);
        }
        else {
            send(request, "У вас не получилось снять " + equipment.getName());
        }
        resource.update(equipment);
    }

    private void attack(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Room room = (Room) userData.getRoom().get();
        var response = Response.builder().userId(request.getUserId()).text("Выберете врага для атаки:");
        for(Resource resource: room.getEnemies()){
            Creature enemy = (Creature) resource.get();
            response.addObject(enemy.getName()+" "+enemy.getHp()+"hp", resource.id);
        }
        request.response = response.build();
    }

    private void attack(Request request, Resource resource) {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Room room = (Room) userData.getRoom().get();
        Creature creature = (Creature) resource.get();
        LevelManager levelManager = (LevelManager) userData.getLevelManager().get();
        StringBuilder builder = new StringBuilder("Вы наносите ").append(creature.getName()).append(" ")
                .append(player.attack(creature)).append(" урона");
        if(creature.getHp()==0)
        {
            builder.append("\n").append(creature.getName()).append(" повержен");
            levelManager.setExp(levelManager.getExp() + creature.getExp());
            room.getEnemies().remove(resource);
            resource.delete();
            userData.getLevelManager().update(levelManager);
        }
        if(room.getEnemies().isEmpty()){
            userData.state=NONE;
            builder.append("\nВсе враги побеждены");
        }
        userData.getRoom().update(room);
        request.getUserData().update(userData);
        if(creature.getHp()>0) resource.update(creature);
        await(request);
        send(request, builder.append("\n").append(request.response.text).toString());
        if (levelManager.getExp()>=levelManager.getNextLevel())
        {
            levelManager.setNextLevel(levelManager.getNextLevel() + levelManager.getLevel() * 10);
            levelManager.setLevel(levelManager.getLevel() + 1);
            userData.getLevelManager().update(levelManager);
            send(request, userData.getLevelManager());
        }
    }

    private void await(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Room room = (Room) userData.getRoom().get();
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<room.getEnemies().size();i++)
        {
            Creature enemy = (Creature) room.getEnemies().get(i).get();
            builder.append(enemy.getName()).append(" наносит ").append(enemy.attack(player)).append(" урона\n");
            if(player.getHp()==0)
            {
                builder.append("Вы проиграли. Игра окончена.\n/retry - чтобы начать заново");
                break;
            }
        }
        userData.getPlayer().update(player);
        send(request, builder.toString());
    }

    private void open(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Door door = (Door) resource.get();
        userData.room = door.getRoom();
        Room room = (Room) door.getRoom().get();
        userData.getRoom().update(room);
        StringBuilder builder = new StringBuilder("Вы вошли в ").append(room.getName());
        if(!room.getEnemies().isEmpty()) {
            userData.state = COMBAT;
            request.getUserData().update(userData);
            await(request);
            builder.append("\nНа вас напали.\n").append(request.response.text)
                    .append("/attack - атаковать\n/await - пропустить ход");
        }
        request.getUserData().update(userData);
        send(request, builder.toString());
    }

    private void raiseHp(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        player.setMaxHp(player.getMaxHp() + 5);
        player.setHp(player.getHp() + 5);
        userData.getPlayer().update(player);
        request.getUserData().update(userData);
        send(request,"Здоровье увеличено на 5");
    }

    private void raiseAp(Request request) {
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        player.setAp(player.getAp() + 1);
        userData.getPlayer().update(player);
        request.getUserData().update(userData);
        send(request,"Урон увеличен на 1");
    }

    private void use(Request request, Resource resource){
        UserData userData = (UserData) request.getUserData().get();
        Creature player = (Creature) userData.getPlayer().get();
        Consumable consumable = (Consumable) resource.get();
        consumable.useConsumable(player);
        player.getInventory().remove(resource);
        userData.getPlayer().update(player);
        resource.update(consumable);
        send(request, "Вы использовали " + consumable.name);
    }
}
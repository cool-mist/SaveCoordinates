package me.bionicbeanie.mods.savecoords;

import java.util.List;

import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.text.Text;

public interface IKeyBinds {
    public static String DEFAULT = "default";
    public static String PING = "ping";
    public static String PING_LOCK = "ping_lock";
    public static String LIST = "list";
    
    public List<IKeyBinding> getAllBinds();
    public IKeyBinding getKeyBind(String name);
    public void updateKeyBind(String name, Type type, int code);
    
    public interface IKeyBinding{
        
        public String getName();
        public boolean wasPressed();
        public Type getDefaultType();
        public int getDefaultCode();
        public Type getType();
        public int getCode();
        public Text getNameLocalizedText();
        public Text getBoundKeyLocalizedText();
    }
}

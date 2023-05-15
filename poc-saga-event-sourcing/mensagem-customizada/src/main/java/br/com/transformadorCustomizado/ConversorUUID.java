package br.com.transformadorCustomizado;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class ConversorUUID {
		
    public static String uuidBase64ParaUUIDString(String str) {
    	ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(str));
        return new UUID(buffer.getLong(), buffer.getLong()).toString();
    }
}

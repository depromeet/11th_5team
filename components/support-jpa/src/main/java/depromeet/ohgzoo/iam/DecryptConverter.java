package depromeet.ohgzoo.iam;

import depromeet.ohgzoo.iam.util.EncryptUtil;

import javax.persistence.AttributeConverter;

public class DecryptConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return EncryptUtil.encrypt(attribute);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return EncryptUtil.decrypted(dbData);
        } catch (Exception e) {
            return "";
        }
    }
}
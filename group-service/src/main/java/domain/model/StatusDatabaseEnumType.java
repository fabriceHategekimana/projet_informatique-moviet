package domain.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

// Mapping a Java Enum to a database-specific Enumerated column type
// https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
public class StatusDatabaseEnumType extends org.hibernate.type.EnumType{
    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        st.setObject(
                index,
                value != null ?
                        ((Enum) value).name() :
                        null,
                Types.OTHER
        );
    }
}

package generate.internal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class DBInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;

    private String name;

    private String password;

    private String driver;

    private String dbName;

    private String[] tableNames;

    private String tableNamePrefix;
}

package zk;

import java.util.Properties;

/**
 * @author yuh
 * @date 2019-06-19 12:17
 **/
public interface CfgWriter {

    void create(String fileName, Properties prop);

    void delete(String fileName);

    void modify(String fileName, Properties prop);

    Properties load(String fileName);
}

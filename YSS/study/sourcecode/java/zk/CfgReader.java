package zk;

import java.util.Properties;

/**
 * @author yuh
 * @date 2019-06-19 12:17
 **/
public interface CfgReader {

    Properties load(String fileName);

    void watch(String fileName, final Properties prop);
}

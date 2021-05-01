package pa.lab10.optional;


import com.jcraft.jsch.*;

public class SftpTransfer {
    private static final String REMOTE_HOST = "demo.wftpserver.com";
    private static final String USERNAME = "demo-user";
    private static final String PASSWORD = "demo-user";
    private static final int REMOTE_PORT = 2222;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

    public static void main(String[] args) {

        String localFile = "src\\main\\java\\pa\\lab10\\server\\chart\\session\\chart.html";
        String remoteFile = "/pub/pa-lab-10-chart.html";

        Session jSession = null;

        try {

            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            jSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jSession.connect(SESSION_TIMEOUT);

            Channel sftp = jSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            channelSftp.put(localFile, remoteFile);

            channelSftp.exit();

        } catch (JSchException | SftpException e) {

            e.printStackTrace();

        } finally {
            if (jSession != null) {
                jSession.disconnect();
            }
        }

        System.out.println("Transfer was Done");
    }

}

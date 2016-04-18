
package com.android.github.sample.model;


public class PullRequest {

    private String url;
    private String htmlUrl;
    private String diffUrl;
    private String patchUrl;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     * 
     * @param htmlUrl
     *     The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * 
     * @return
     *     The diffUrl
     */
    public String getDiffUrl() {
        return diffUrl;
    }

    /**
     * 
     * @param diffUrl
     *     The diff_url
     */
    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    /**
     * 
     * @return
     *     The patchUrl
     */
    public String getPatchUrl() {
        return patchUrl;
    }

    /**
     * 
     * @param patchUrl
     *     The patch_url
     */
    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

}

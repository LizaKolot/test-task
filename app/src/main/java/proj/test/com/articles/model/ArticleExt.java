
package proj.test.com.articles.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import proj.test.com.articles.model.utils.ArrayDeserializer;

public class ArticleExt extends Article{

   /* @SerializedName("url")
    @Expose
    private String url;*/
    @SerializedName("column")
    @Expose
    private Object column;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("byline")
    @Expose
    private String byline;
    /*@SerializedName("title")
    @Expose
    private String title;*/
    @SerializedName("abstract")
    @Expose
    private String _abstract;
   /* @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;*/
    @SerializedName("asset_id")
    @Expose
    private Long assetId;
    @SerializedName("total_shares")
    @Expose
    private Integer totalShares;

    @JsonAdapter(value = ArrayDeserializer.class)
    @SerializedName("des_facet")
    @Expose
    private List<String> desFacet = null;

    @JsonAdapter(value = ArrayDeserializer.class)
    @SerializedName("org_facet")
    @Expose
    private List<String> orgFacet = null;

    @JsonAdapter(value = ArrayDeserializer.class)
    @SerializedName("per_facet")
    @Expose
    private List<String> perFacet = null;

    @JsonAdapter(value = ArrayDeserializer.class)
    @SerializedName("geo_facet")
    @Expose
    private List<String> geoFacet = null;

    @JsonAdapter(value = ArrayDeserializer.class)
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;



    public Object getColumn() {
        return column;
    }

    public void setColumn(Object column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }
    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }



    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Integer getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(Integer totalShares) {
        this.totalShares = totalShares;
    }

    public List<String> getDesFacet() {
        return desFacet;
    }

    public void setDesFacet(List<String> desFacet) {
        this.desFacet = desFacet;
    }

    public List<String> getOrgFacet() {
        return orgFacet;
    }

    public void setOrgFacet(List<String> orgFacet) {
        this.orgFacet = orgFacet;
    }

    public List<String> getPerFacet() {
        return perFacet;
    }

    public void setPerFacet(List<String> perFacet) {
        this.perFacet = perFacet;
    }

    public List<String> getGeoFacet() {
        return geoFacet;
    }

    public void setGeoFacet(List<String> geoFacet) {
        this.geoFacet = geoFacet;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

}
package com.github.dreamhead.moco.parser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.github.dreamhead.moco.RestSetting;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.github.dreamhead.moco.parser.model.RestBaseSetting.toSetting;
import static com.github.dreamhead.moco.parser.model.RestSubResourceSetting.toSubResourceSetting;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.toArray;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResourceSetting {
    private String name;
    private List<RestGetSetting> get;
    private List<RestPostSetting> post;
    private List<RestPutSetting> put;
    private List<RestDeleteSetting> delete;
    private List<RestHeadSetting> head;
    private List<RestPatchSetting> patch;
    private List<RestSubResourceSetting> resource;

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public RestSetting[] getSettings() {
        return toArray(concat(asRestSetting(get), asRestSetting(post),
                        asRestSetting(put), asRestSetting(delete),
                        asRestSetting(head), asRestSetting(patch), asSubRestSetting(resource)),
                RestSetting.class);
    }

    private Iterable<RestSetting> asRestSetting(final List<? extends RestBaseSetting> setting) {
        if (setting == null || setting.isEmpty()) {
            return ImmutableList.of();
        }

        return from(setting).transform(toSetting());
    }

    private Iterable<RestSetting> asSubRestSetting(final List<RestSubResourceSetting> setting) {
        if (setting == null || setting.isEmpty()) {
            return ImmutableList.of();
        }

        return from(setting).transform(toSubResourceSetting());
    }

}
